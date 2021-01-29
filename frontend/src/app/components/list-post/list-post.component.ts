import { Component, OnInit } from '@angular/core';
import {Post} from '../../model/Post';
import {PostService} from '../../service/post.service';

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
    this.postService.getPosts().subscribe(postResponse => this.posts = postResponse);
    console.warn(this.posts[0].user);
    console.warn(this.posts[0].postCreationDate);
    console.warn(this.posts[0].user);
    console.warn(this.posts[0].user);
    console.warn(this.posts[0].user);
    console.warn(this.posts[0].user);
    console.warn(this.posts[0].user);

  }
}
