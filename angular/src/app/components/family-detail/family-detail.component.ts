import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Family } from '../../models/family.model';
import { Father } from '../../models/father.model';
import { Child } from '../../models/child.model';

import { FamilyService } from '../../services/family.service';
import { FatherService } from '../../services/father.service';
import { ChildService } from '../../services/child.service';

@Component({
  selector: 'app-family-detail',
  templateUrl: './family-detail.component.html'
})
export class FamilyDetailComponent implements OnInit {

  family: Family;

  constructor(
    private familyService: FamilyService,
    private fatherService: FatherService,
    private childService: ChildService,
    private location: Location,
    private activatedRoute: ActivatedRoute
  ) { }

  ngOnInit() {
    const familyId = +this.activatedRoute.snapshot.paramMap.get('id');
    this.familyService.getFamilyById(familyId)
      .subscribe((family: Family) => this.family = family);
  }

  goBack() {
    this.location.back();
  }
}
