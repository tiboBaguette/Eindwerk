import {AfterContentChecked, Component, OnInit} from '@angular/core';
import {Post} from '../../model/Post';
import {PostService} from '../../service/post.service';
import {HttpErrorResponse} from '@angular/common/http';
import {UserService} from "../../service/user.service";
import {User} from "../../model/User";

@Component({
  selector: 'app-list-post',
  templateUrl: './list-post.component.html',
  styleUrls: ['./list-post.component.css']
})
export class ListPostComponent implements OnInit, AfterContentChecked {
  posts: Post[] = [];
  isLoggedIn: boolean | undefined;
  user: User | undefined;
  constructor(
    private postService: PostService,
    private userService: UserService
  ) {}
  public randomInt = (min: number, max: number): number => {
    return Math.floor(Math.random() * (max - min + 1) + min);
  }
  ngOnInit(): void {

    this.postService.getPosts().subscribe(
      response => this.posts = response,
      error => this.handleError(error),
    );

  }

  ngAfterContentChecked(): void {
    this.user = this.userService.user;
    this.isLoggedIn = this.user !== undefined;
  }
  handleError(error: HttpErrorResponse): void {}


}
