import { Component, OnInit } from '@angular/core';
import { CoursesService } from '../services/courses.service';
import { Course } from '../model/course';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.scss']
})
export class CoursesComponent implements OnInit {

  courses$: Observable<Course[]>;
  displayedColumns: string[] = ['name', 'category'];

  constructor(private coursesService: CoursesService) {
    this.courses$ = this.coursesService.list();
  }

  ngOnInit(): void {}

}
