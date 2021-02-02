import { Component, OnInit } from '@angular/core';
import {Post} from '../../model/Post';
import {PostService} from '../../service/post.service';
import {Comment} from '../../model/Comment';
import {ActivatedRoute, Router} from '@angular/router';
import {CommentService} from '../../service/comment.service';
import {HttpErrorResponse} from '@angular/common/http';
import {User} from "../../model/User";
import {UserService} from "../../service/user.service";

@Component({
  selector: 'app-post-details',
  templateUrl: './post-details.component.html',
  styleUrls: ['./post-details.component.css']
})
export class PostDetailsComponent implements OnInit {
  post: Post = new Post();
  comments: Comment[] = [];
  activeUser: User | undefined;

  constructor(
    private postService: PostService,
    private commentService: CommentService,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router,
  ) { }

  ngOnInit(): void {
    this.activeUser = this.userService.user;

    this.route.params.subscribe(params => {
      this.postService.getPostById(params.id).subscribe(postResponse => this.post = postResponse);
      this.commentService.getComments(params.id).subscribe(commentResponse => this.comments = commentResponse);
    });
  }

  deletePost(): void {
    this.postService.deletePost(this.post).subscribe(
      response => this.post = response,
      error => this.handleError(error),
    );
  }

  handleError(error: HttpErrorResponse): void {
    if (error.status === 200) { // ok
      this.router.navigateByUrl('list-posts');
    }
  }
}
