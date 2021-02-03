import { Component, OnInit } from '@angular/core';
import {Post} from '../../model/Post';
import {PostService} from '../../service/post.service';
import {Comment} from '../../model/Comment';
import {ActivatedRoute, Router} from '@angular/router';
import {CommentService} from '../../service/comment.service';
import {HttpErrorResponse} from '@angular/common/http';
import {User} from '../../model/User';
import {UserService} from '../../service/user.service';
import {FormBuilder} from '@angular/forms';

@Component({
  selector: 'app-post-details',
  templateUrl: './post-details.component.html',
  styleUrls: ['./post-details.component.css']
})
export class PostDetailsComponent implements OnInit {
  isError: boolean | undefined;
  post: Post = new Post();
  comment: Comment = new Comment();
  posts: Post[] = [];
  comments: Comment[] = [];
  activeUser: User | undefined;

  createCommentForm = this.formBuilder.group({
    content: '',
  });
  constructor(
    private formBuilder: FormBuilder,
    private postService: PostService,
    private commentService: CommentService,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router,
  ) { }

  ngOnInit(): void {
    this.activeUser = this.userService.user;
    this.route.params.subscribe(params => {
      this.postService.getPostById(params.id).toPromise().then((postResponse) => this.post = postResponse);
      this.commentService.getComments(params.id).toPromise().then((commentResponse) => this.comments = commentResponse);
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

  createComment(): void {
    if (this.userService.user === undefined) {
      this.isError = true;
    } else {
      // grab the id from the url
      this.route.params.subscribe(params => {
        this.comment.content = this.createCommentForm.controls.content.value;
        this.comment.post = this.post;
        // create comment
        this.commentService.createComment(this.comment).toPromise().then(() => {
          // Get the updated list on the post-details page after creating a new comment
          this.commentService.getComments(params.id).toPromise().then((commentResponse) => this.comments = commentResponse);
          this.postService.getPostById(params.id).toPromise().then((postResponse) => this.post = postResponse);
          this.router.navigateByUrl('post-details/' + params.id);
        });
      });
    }
  }
}
