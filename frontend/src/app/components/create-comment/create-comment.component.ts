import { Component, OnInit } from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {UserService} from '../../service/user.service';
import {CommentService} from '../../service/comment.service';
import {ActivatedRoute, Router} from '@angular/router';
import {PostService} from '../../service/post.service';
import {Post} from '../../model/Post';

@Component({
  selector: 'app-create-comment',
  templateUrl: './create-comment.component.html',
  styleUrls: ['./create-comment.component.css']
})
export class CreateCommentComponent implements OnInit {
  isError: boolean | undefined;
  post: Post = new Post();

  createCommentForm = this.formBuilder.group({
    content: '',
    user: this.userService.user,
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
        this.commentService.createComment(this.createCommentForm.value).subscribe(() => {

          this.router.navigateByUrl('post-details/:' + params.id);
        });
      });
    }
  }
}
