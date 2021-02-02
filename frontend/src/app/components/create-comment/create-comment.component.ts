import { Component, OnInit } from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {UserService} from '../../service/user.service';
import {CommentService} from '../../service/comment.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-create-comment',
  templateUrl: './create-comment.component.html',
  styleUrls: ['./create-comment.component.css']
})
export class CreateCommentComponent implements OnInit {
  isError: boolean | undefined;

  createCommentForm = this.formBuilder.group({
    content: '',
  });

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private commentService: CommentService,
    private router: Router,
    ) { }

  ngOnInit(): void {
  }

  createComment(): void {
    if (this.userService.user === undefined) {
      this.isError = true;
    } else {
      this.commentService.createComment(this.createCommentForm.value).subscribe(() => {
        this.router.navigateByUrl('post-details');
      });
    }
  }
}
