import { Component, OnInit } from '@angular/core';
import {Post} from '../../model/Post';
import {PostService} from '../../service/post.service';
import {User} from '../../model/User';
import {Comment} from '../../model/Comment';

@Component({
  selector: 'app-post-details',
  templateUrl: './post-details.component.html',
  styleUrls: ['./post-details.component.css']
})
export class PostDetailsComponent implements OnInit {
  posts: Post[] = [];
  post: Post = new Post();
  user: User = new User();
  comment: Comment = new Comment();
  comment1: Comment = new Comment();
  comments: Comment[] = [];
  constructor(postService: PostService) { }

  ngOnInit(): void {
    this.post.title = 'Wall Street Bets Details Mock';
    this.post.content = 'This is the content of the details page post';
    this.post.postCreationDate = new Date();
    this.user.username = 'Lektro';
    this.comment.content = 'Comment Content Block';
    this.comment1.content = 'Comment Content Block 2';
    this.post.user = this.user;
    this.posts.push(this.post);
    this.comments.push(this.comment);
    this.comments.push(this.comment1);
  }
  // this.postService.getPosts().subscribe(postResponse => this.posts = postResponse);
  // this.postService.getComments().subscribe(commentResponse => this.comments = commentResponse);
}
