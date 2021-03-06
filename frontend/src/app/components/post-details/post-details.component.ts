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
  posts: Post[] = [];
  comments: Comment[] = [];
  likes: number | undefined;
  activeUser: User | undefined;

  createCommentForm = this.formBuilder.group({
    content: '',
    user: this.userService.user?.username,
    post: new Post(),
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
      this.loadPost(params.id);
      this.loadComments(params.id);
      this.getLikes(params.id);
    });
  }

  createComment(): void {
    if (this.userService.user !== undefined) {
      this.route.params.subscribe(params => {
        this.createCommentForm.controls.post.setValue(this.post);

        this.commentService.createComment(this.createCommentForm.value).subscribe(
          () => this.createCommentForm.reset(),
          error => this.handleError(error),
          () => this.loadComments(params.id),
        );
      });
    } else {
      this.isError = true;
    }
  }

  like(): void {
    this.route.params.subscribe(params => {
      this.likePost(params.id);
    });
  }

  unLike(): void {
    this.route.params.subscribe(params => {
      this.unLikePost(params.id);
    });
  }

  loadPost(id: number): void {
    this.postService.getPostById(id).subscribe(
      response => this.post = response,
      error => this.handleError(error),
      () => this.router.navigateByUrl('post-details/' + id),
    );
  }

  loadComments(id: number): void {
    this.commentService.getComments(id).subscribe(
      response => this.comments = response,
      error => this.handleError(error),
    () => this.router.navigateByUrl('post-details/' + id),
    );
  }

  deletePost(): void {
    this.postService.deletePost(this.post).subscribe(
      response => this.post = response,
      error => {
        if (error.status === 200) { // ok
          this.router.navigateByUrl('list-posts');
        } else {
          this.handleError(error);
        }
      }
    );
  }

  likePost(postId: number): void {
    this.postService.likePost(postId, this.activeUser?.username).subscribe(
      response => console.warn(response),
      error => {
        if (error.status === 201) { // created
          this.getLikes(postId);
        } else {
          this.handleError(error);
        }
      }
    );
  }

  unLikePost(postId: number): void {
    this.postService.unLikePost(postId, this.activeUser?.username).subscribe(
      response => console.warn(response),
      error => {
        if (error.status === 200) { // ok
          this.getLikes(postId);
        } else {
          this.handleError(error);
        }
      }
    );
  }

  getLikes(postId: number): void {
    this.postService.getLikes(postId).subscribe(
      response => this.likes = response,
      error => this.handleError(error),
    );
  }

  handleError(error: HttpErrorResponse): void {
    console.warn(error);
  }
}
