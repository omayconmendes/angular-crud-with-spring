import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { delay, first, tap } from 'rxjs/operators';

import { Course } from '../model/course';

@Injectable({
  providedIn: 'root'
})
export class CoursesService {

  constructor(private httpClient: HttpClient) { }

  list(): Observable<Course[]> {
    return this.httpClient.get<Course[]>('api/courses')
    .pipe(
      first(),
      delay(3000),
      tap(courses => console.log(courses))
    );
  };
}
