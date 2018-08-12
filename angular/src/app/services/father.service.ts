import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Father } from '../models/father.model';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
}

@Injectable({
  providedIn: 'root'
})
export class FatherService {

  constructor(
    private http: HttpClient
  ) {}


  getFathers(): Observable<Father[]> {
    return this.http.get<Father[]>('/api/father').pipe(
      tap(_ => this.log('fetched fathers')),
      catchError(this.handleError('getFathers', []))
    );
  }

  getFatherById(id: number): Observable<Father> {
    return this.http.get<Father>(`/api/father/${id}`).pipe(
      tap(_ => this.log(`fetched father with id=${id}`)),
      catchError(this.handleError<Father>('getFatherById'))
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
