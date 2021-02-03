import { Component, OnInit } from '@angular/core';
import {PostService} from '../../service/post.service';
import {FormBuilder} from '@angular/forms';
import {UserService} from '../../service/user.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Post} from "../../model/Post";

@Component({
  selector: 'app-edit-post',
  templateUrl: './edit-post.component.html',
  styleUrls: ['./edit-post.component.css']
})
export class EditPostComponent implements OnInit {

  isError: boolean | undefined;
  post: Post | undefined;
  id: number | undefined;
  editPostForm = this.formBuilder.group({
    title: '',
    content: '',
    user: this.userService.user?.id,
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

  editPost(): void {
    if (this.userService.user === undefined) {
      this.isError = true;
    } else {
      this.postService.createPost(this.editPostForm.value).subscribe();
      this.editPostForm.reset();
      this.router.navigateByUrl('post-details');
    }
  }
}
