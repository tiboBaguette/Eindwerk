import {Component, OnInit} from '@angular/core';
import {PostService} from '../../service/post.service';
import {FormBuilder} from '@angular/forms';
import {UserService} from '../../service/user.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Post} from '../../model/Post';
import {HttpErrorResponse} from '@angular/common/http';

@Component({
  selector: 'app-edit-post',
  templateUrl: './edit-post.component.html',
  styleUrls: ['./edit-post.component.css']
})
export class EditPostComponent implements OnInit {

  isError: boolean | undefined;
  post: Post = new Post();
  postId: number | undefined;

  editPostForm = this.formBuilder.group({
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
    this.route.params.subscribe(params => {
      this.postService.getPostById(params.id).subscribe(
        response => this.post = response,
        error => this.handleError(error),
        () => this.updateFormValues()
      );
      this.postId = params.id;
    });
  }

  updateFormValues(): void {
    this.editPostForm.controls.title.setValue(this.post?.title);
    this.editPostForm.controls.content.setValue(this.post?.content);
    this.editPostForm.controls.category.setValue(this.post?.category?.name);
  }

  editPost(): void {
    if (this.userService.user === undefined) {
      this.isError = true;
    } else {
      this.postService.editPost(this.editPostForm.value, this.postId).subscribe();
      this.editPostForm.reset();
      this.router.navigateByUrl('post-details/' + this.postId);
    }
  }

  handleError(error: HttpErrorResponse): void {}
}
