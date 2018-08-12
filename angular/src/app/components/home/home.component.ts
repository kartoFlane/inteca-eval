import { Component, OnInit } from '@angular/core';

import { FamilyService } from '../../services/family.service';
import { FatherService } from '../../services/father.service';
import { ChildService } from '../../services/child.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html'
})
export class HomeComponent implements OnInit {
  familyCount: number = 0;
  fatherCount: number = 0;
  childCount: number = 0;

  constructor(
    private familyService: FamilyService,
    private fatherService: FatherService,
    private childService: ChildService
  ) { }

  ngOnInit() {
    this.getData();
  }

  getData() {
    this.familyService.getFamilies()
      .subscribe(families => this.familyCount = families.length);
    this.fatherService.getFathers()
      .subscribe(fathers => this.fatherCount = fathers.length);
    this.childService.getChildren()
      .subscribe(children => this.childCount = children.length);
  }
}
