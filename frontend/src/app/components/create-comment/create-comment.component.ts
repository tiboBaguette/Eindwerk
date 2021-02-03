import { Component, OnInit } from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {UserService} from '../../service/user.service';
import {CommentService} from '../../service/comment.service';
import {ActivatedRoute, Router} from '@angular/router';
import {PostService} from '../../service/post.service';
import {Post} from '../../model/Post';
import {Comment} from '../../model/Comment';

@Component({
  selector: 'app-create-comment',
  templateUrl: './create-comment.component.html',
  styleUrls: ['./create-comment.component.css']
})
export class CreateCommentComponent implements OnInit {
  isError: boolean | undefined;
  post: Post = new Post();
  comment: Comment = new Comment();

  createCommentForm = this.formBuilder.group({
    content: '',
  });

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private postService: PostService,
    private commentService: CommentService,
    private router: Router,
    private route: ActivatedRoute
    ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.postService.getPostById(params.id).subscribe(postResponse => this.post = postResponse);
    });
  }

  createComment(): void {
    if (this.userService.user === undefined) {
      this.isError = true;
    } else {
      this.route.params.subscribe(params => {
        this.comment.content = this.createCommentForm.controls.content.value;
        this.comment.post = this.post;

        this.commentService.createComment(this.comment).subscribe(() => {
          this.router.navigateByUrl('post-details/' + params.id);
        });
      });
    }
  }
}
