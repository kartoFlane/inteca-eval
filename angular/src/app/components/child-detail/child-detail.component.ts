import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Child } from '../../models/child.model';
import { ChildService } from '../../services/child.service';

@Component({
  selector: 'app-child-detail',
  templateUrl: './child-detail.component.html'
})
export class ChildDetailComponent implements OnInit {

  child: Child;

  constructor(
    private childService: ChildService,
    private location: Location,
    private activatedRoute: ActivatedRoute
  ) { }

  ngOnInit() {
    const childId = +this.activatedRoute.snapshot.paramMap.get('id');
    this.childService.getChildById(childId)
      .subscribe((child: Child) => {
        this.child = child;
      });
  }

  goBack() {
    this.location.back();
  }
}
