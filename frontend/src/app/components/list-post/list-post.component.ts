import { Component, OnInit } from '@angular/core';
import {Post} from '../../model/Post';
import {PostService} from '../../service/post.service';
import {HttpErrorResponse} from '@angular/common/http';

@Component({
  selector: 'app-list-post',
  templateUrl: './list-post.component.html',
  styleUrls: ['./list-post.component.css']
})
export class ListPostComponent implements OnInit {
  posts: Post[] = [];

  constructor(
    private postService: PostService,
  ) {}

  ngOnInit(): void {
    this.postService.getPosts().subscribe(
      response => this.posts = response,
      error => this.handleError(error),
    );
  }

  handleError(error: HttpErrorResponse): void {}
}
