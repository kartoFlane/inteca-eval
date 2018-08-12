import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Child } from '../models/child.model';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
}

@Injectable({
  providedIn: 'root'
})
export class ChildService {

  constructor(
    private http: HttpClient
  ) {}


  getChildren(): Observable<Child[]> {
    return this.http.get<Child[]>('/api/child').pipe(
      tap(_ => this.log('fetched children')),
      catchError(this.handleError('getChildren', []))
    );
  }

  getChildById(id: number): Observable<Child> {
    return this.http.get<Child>(`/api/child/${id}`).pipe(
      tap(_ => this.log(`fetched child with id=${id}`)),
      catchError(this.handleError<Child>('getChildById'))
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
