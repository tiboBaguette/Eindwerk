import {
  AfterContentChecked,
  AfterContentInit, AfterViewChecked,
  AfterViewInit,
  Component,
  DoCheck,
  OnChanges,
  OnInit
} from '@angular/core';
import {Post} from '../../model/Post';
import {PostService} from '../../service/post.service';
import {HttpErrorResponse} from '@angular/common/http';
import {UserService} from '../../service/user.service';
import {User} from '../../model/User';

@Component({
  selector: 'app-list-post',
  templateUrl: './list-post.component.html',
  styleUrls: ['./list-post.component.css']
})
export class ListPostComponent implements OnInit, AfterViewInit{
  posts: Post[] = [];
  isLoggedIn: boolean | undefined;
  user: User | undefined;

  constructor(
    private postService: PostService,
    private userService: UserService
  ) {}
  ngOnInit(): void {
    this.postService.getPosts().subscribe(
      response => this.posts = response,
      error => this.handleError(error),
      // () => console.warn(this.posts),
    );
  }

  ngAfterViewInit(): void {
    this.user = this.userService.user;
    this.isLoggedIn = this.user !== undefined;
  }

  handleError(error: HttpErrorResponse): void {}

}
