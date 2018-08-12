import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

import { Observable, of, forkJoin } from 'rxjs';
import { catchError, map, flatMap, concatMap, tap, switchMap } from 'rxjs/operators';

import { Family } from '../models/family.model';
import { Father } from '../models/father.model';
import { Child } from '../models/child.model';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
}

@Injectable({
  providedIn: 'root'
})
export class FamilyService {

  constructor(
    private http: HttpClient
  ) {}


  getFamilies(): Observable<Family[]> {
    return this.http.get<Family[]>('/api/family').pipe(
      tap(_ => this.log('fetched families')),
      catchError(this.handleError('getFamilies', []))
    );
  }

  getFamilyById(id: number): Observable<Family> {
    return this.http.get<Family>(`/api/family/${id}`).pipe(
      tap(_ => this.log(`fetched family with id=${id}`)),
      catchError(this.handleError<Family>('getFamilyById'))
    );
  }

  searchFamily(input: Child): Observable<Family[]> {
    let params = new HttpParams()
      .set("firstName", input.firstName)
      .set("secondName", input.secondName)
      .set("birthDate", input.birthDate)
      .set("pesel", input.pesel)
      .set("sex", input.sex);

    // Cleanup keys which should not be set.
    params.keys()
      .filter(key => params.get(key) === null)
      .forEach(key => params = params.delete(key));

    const options = { headers: httpOptions.headers, params: params };

    return this.http.get<Family[]>('/api/family', options).pipe(
      tap(_ => this.log(`performing family search`)),
      catchError(this.handleError('searchFamily', []))
    );
  }

  createFamily(input: Family): Observable<Family> {
    return this.http.post<Family>('/api/family', {}, httpOptions).pipe(
      tap(family => this.log(`created family with id=${family.id}`)),
      concatMap(family => {
        const options = {
          headers: httpOptions.headers,
          params: new HttpParams().set("familyId", `${family.id}`)
        };

        const father$ = this.http.post<Father>('/api/father', input.father, options).pipe(
          tap(father => {
            family.father = father
            this.log(`created father with id=${father.id}`);
          })
        );

        const children$ = [];
        input.children.forEach(inputChild => {
          const child$ = this.http.post<Child>('/api/child', inputChild, options).pipe(
            tap(child => {
              family.children.push(child)
              this.log(`created child with id=${child.id}`);
            })
          )

          children$.push(child$)
        });

        // We only care about being notified that the father/child add
        // operations have finished. We don't care about their result,
        // so we always return the family object.
        return forkJoin([ father$ ].concat(children$)).pipe(
          map(results => family)
        );
      }),
      catchError(this.handleError<Family>('createFamily'))
    );
  }

// --------------------------------------------------------------------

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);

      return of(result as T);
    };
  }

  private log(message: string) {
    console.log(message);
  }
}
