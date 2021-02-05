import {Component, OnInit} from '@angular/core';
import {Post} from '../../model/Post';
import {PostService} from '../../service/post.service';
import {HttpErrorResponse} from '@angular/common/http';
import {UserService} from '../../service/user.service';
import {User} from '../../model/User';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-list-post',
  templateUrl: './list-post.component.html',
  styleUrls: ['./list-post.component.css']
})
export class ListPostComponent implements OnInit {
  posts: Post[] = [];
  isLoggedIn: boolean | undefined;
  showCategoryName: boolean | undefined;
  user: User | undefined;

  constructor(
    private postService: PostService,
    private userService: UserService,
    private route: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.user = this.userService.user;
    this.isLoggedIn = this.user !== undefined;

    this.route.params.subscribe(params => {
      if (params.category === undefined) {
        this.postService.getPosts().subscribe(
          response => this.posts = response,
          error => this.handleError(error),
        );
      } else {
        this.showCategoryName = true;
        this.postService.getPostByCategory(params.category).subscribe(
          response => this.posts = response,
          error => this.handleError(error),
        );
      }
    });
  }

  handleError(error: HttpErrorResponse): void {}

}
