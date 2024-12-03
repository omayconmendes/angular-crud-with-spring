import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { first, tap } from 'rxjs/operators';

import { Course } from '../model/course';
import { CoursePage } from '../model/course-page';

@Injectable({
  providedIn: 'root'
})
export class CoursesService {

  private readonly API = 'api/courses/';

  constructor(private httpClient: HttpClient) { }

  list(page = 0, pageSize = 10): Observable<CoursePage> {
    return this.httpClient.get<CoursePage>(this.API, { params: { page, size: pageSize }})
    .pipe(
      first()
    );
  };

  getById(id: string) {
    return this.httpClient.get<Course>(`api/courses/${id}`);
  }

  save (record: Partial<Course>) {
    if (record._id) {
      return this.update(record);
    }
    return this.create(record);
  }

  private create(record: Partial<Course>) {
    return this.httpClient.post<Course>('api/courses/', record)
    .pipe(
      first()
    );
  }

  private update(record: Partial<Course>) {
    return this.httpClient.put<Course>(`api/courses/${record._id}`, record).pipe(
      first()
    );
  }

  remove(id: string) {
    return this.httpClient.delete(`api/courses/${id}`).pipe(
      first()
    );
  }
}
