import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Father } from '../../models/father.model';
import { FatherService } from '../../services/father.service';

@Component({
  selector: 'app-father-detail',
  templateUrl: './father-detail.component.html'
})
export class FatherDetailComponent implements OnInit {

  father: Father;

  constructor(
    private fatherService: FatherService,
    private location: Location,
    private activatedRoute: ActivatedRoute
  ) { }

  ngOnInit() {
    const fatherId = +this.activatedRoute.snapshot.paramMap.get('id');
    this.fatherService.getFatherById(fatherId)
      .subscribe((father: Father) => {
        this.father = father;
      });
  }

  goBack() {
    this.location.back();
  }
}
