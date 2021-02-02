import { Component, OnInit } from '@angular/core';
import {Post} from '../../model/Post';
import {PostService} from '../../service/post.service';
import {Comment} from '../../model/Comment';
import {ActivatedRoute} from '@angular/router';
import {CommentService} from '../../service/comment.service';

@Component({
  selector: 'app-post-details',
  templateUrl: './post-details.component.html',
  styleUrls: ['./post-details.component.css']
})
export class PostDetailsComponent implements OnInit {
  post: Post = new Post();
  comments: Comment[] = [];

  constructor(
    private postService: PostService,
    private commentService: CommentService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.postService.getPostById(params.id).subscribe(postResponse => this.post = postResponse);
      this.commentService.getComments(params.id).subscribe(commentResponse => this.comments = commentResponse);
    });
  }
}
