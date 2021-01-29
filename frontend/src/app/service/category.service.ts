import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {Category} from '../model/Category';
import {HttpClient} from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private http: HttpClient) { }

  createCategory(category: Category): Observable<Category> {
    return this.http.post<Category>('http://localhost:8080/api/categories/add', category);
  }

  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>('http://localhost:8080/api/categories/show');
  }
}
