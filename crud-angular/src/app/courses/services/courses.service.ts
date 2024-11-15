import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { first, tap } from 'rxjs/operators';

import { Course } from '../model/course';

@Injectable({
  providedIn: 'root'
})
export class CoursesService {

  constructor(private httpClient: HttpClient) { }

  list(): Observable<Course[]> {
    return this.httpClient.get<Course[]>('api/courses/')
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
    return this.httpClient.post<Course>('api/courses', record)
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
