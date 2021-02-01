import { Component, OnInit } from '@angular/core';
import {PostService} from '../../service/post.service';
import {FormBuilder} from '@angular/forms';
import {UserService} from '../../service/user.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {
  isError: boolean | undefined;

  createPostForm = this.formBuilder.group({
    title: '',
    content: '',
    user: this.userService.user,
    category: '',
  });

  constructor(
    private postService: PostService,
    private formBuilder: FormBuilder,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router,
  ) {
    this.userService.setActiveUser(this.userService.user);
  }

  ngOnInit(): void {
  }

  createPost(): void {
    if (this.userService.user === undefined) {
      this.isError = true;
    } else {
      this.postService.createPost(this.createPostForm.value).subscribe(() => {
        this.router.navigateByUrl('list-posts');
      });
    }
  }
}
