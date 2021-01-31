import { Component, OnInit } from '@angular/core';
import {Post} from '../../model/Post';
import {PostService} from '../../service/post.service';
import {User} from '../../model/User';
import {UserService} from "../../service/user.service";

@Component({
  selector: 'app-list-post',
  templateUrl: './list-post.component.html',
  styleUrls: ['./list-post.component.css']
})
export class ListPostComponent implements OnInit {
  posts: Post[] = [];

  // mock data voor te testen
   // user: User = new User();
   // post: Post = new Post();

  constructor(
    private postService: PostService,
  ) {}

  ngOnInit(): void {
    // mock data voor te testen
    // this.user.username = 'Lektro';
    // this.post.title = 'Post Title';
    // this.post.content = 'This is a wider card with supporting text below as a natural lead-in to additional content';
    // this.post.postCreationDate = new Date();
    // this.post.user = this.user;
    // this.posts.push(this.post);
    // this.posts.push(this.post);
    // this.posts.push(this.post);
    // this.posts.push(this.post);
    // this.posts.push(this.post);

    this.postService.getPosts().subscribe(postResponse => this.posts = postResponse);
  }
}
