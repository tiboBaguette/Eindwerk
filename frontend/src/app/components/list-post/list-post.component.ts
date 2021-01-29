import { Component, OnInit } from '@angular/core';
import {Post} from '../../model/Post';
import {PostService} from '../../service/post.service';
import {User} from '../../model/User';

@Component({
  selector: 'app-list-post',
  templateUrl: './list-post.component.html',
  styleUrls: ['./list-post.component.css']
})
export class ListPostComponent implements OnInit {
  posts: Post[] = [];

  // mock data voor te testen
  user: User = new User();
  post: Post = new Post();

  constructor(
    private postservice: PostService,
  ) {}

  ngOnInit(): void {
    // mock data voor te testen
    this.user.username = 'username';
    this.post.title = 'post title';
    this.post.content = 'This is a wider card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.';
    this.post.postCreationDate = new Date();
    this.post.user = this.user;
    this.posts.push(this.post);
    this.posts.push(this.post);
    this.posts.push(this.post);
    this.posts.push(this.post);
    this.posts.push(this.post);

    // this.postservice.getPosts().subscribe(postResponse => this.posts = postResponse);
  }
}
