import { Component, OnInit } from '@angular/core';
import {Post} from '../../model/Post';
import {PostService} from '../../service/post.service';
import {Comment} from '../../model/Comment';

@Component({
  selector: 'app-post-details',
  templateUrl: './post-details.component.html',
  styleUrls: ['./post-details.component.css']
})
export class PostDetailsComponent implements OnInit {
  post: Post = new Post();
  comments: Comment[] = [];

  // mock
  comment: Comment = new Comment();
  comment1: Comment = new Comment();

  constructor(private postService: PostService) { }

  ngOnInit(): void {
    this.comment.content = 'Comment Content Block';
    this.comment1.content = 'Comment Content Block 2';
    this.comments.push(this.comment);
    this.comments.push(this.comment1);

    this.postService.getPostById(1).subscribe(postResponse => this.post = postResponse);
    // this.postService.getComments().subscribe(commentResponse => this.comments = commentResponse);
  }
}
