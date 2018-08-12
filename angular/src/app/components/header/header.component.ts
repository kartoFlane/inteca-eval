import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html'
})
export class HeaderComponent implements OnInit {

  menuItems: any[];

  constructor() { }

  ngOnInit() {
    this.loadMenus();
  }

  private loadMenus(): void {
    this.menuItems = [
      { link: "/", name: "home" },
      { link: "/family/search", name: "search families" },
      { link: "/family/new", name: "add family" }
    ];
  }
}
