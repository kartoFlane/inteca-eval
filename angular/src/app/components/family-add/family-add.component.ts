import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { concat } from 'rxjs/operators';

import { FamilyService } from '../../services/family.service';
import { FatherService } from '../../services/father.service';
import { ChildService } from '../../services/child.service';

import { Family } from '../../models/family.model';
import { Father } from '../../models/father.model';
import { Child } from '../../models/child.model';

@Component({
  selector: 'app-family-add',
  templateUrl: './family-add.component.html'
})
export class FamilyAddComponent implements OnInit {

  // Define constants here, so that they can be accessed in the template file.
  readonly STEP_ABORT =    0;
  readonly STEP_FATHER =   1;
  readonly STEP_CHILDREN = 2;
  readonly STEP_FINISH =   3;

  step: number = this.STEP_FATHER;
  prevText: string = "";
  nextText: string = "";

  input: Family;

  constructor(
    private familyService: FamilyService,
    private fatherService: FatherService,
    private childService: ChildService,
    private router: Router,
  ) {}

  ngOnInit() {
    this.createFamily();
    this.executeStep(this.step);
  }

  createFamily() {
    this.input = {
      father:   {} as Father,
      children: []
    } as Family;
  }

  createChild() {
    // Compiler complains when some fields are missing,
    // probably due to an additional field being included
    // here (collapsed)
    this.input.children.push({
      id:         undefined,
      firstName:  undefined,
      secondName: undefined,
      birthDate:  undefined,
      pesel:      undefined,
      sex:        "",
      collapsed:  false
    } as Child);
  }

  removeChild(child: Child) {
    const index = this.input.children.indexOf(child, 0);
    if (index > -1) {
      this.input.children.splice(index, 1)
    }
  }

  nextStep() {
    this.step = Math.min(this.STEP_FINISH, this.step + 1);
    this.executeStep(this.step);
  }

  prevStep() {
    this.step = Math.max(this.STEP_ABORT, this.step - 1);
    this.executeStep(this.step);
  }

  private executeStep(step: number) {
    switch (step) {
      case this.STEP_ABORT:
        this.stepAbort();
      break;

      case this.STEP_FATHER:
        this.stepFather();
      break;

      case this.STEP_CHILDREN:
        this.stepChildren();
      break;

      case this.STEP_FINISH:
        this.stepFinish();
      break;
    }
  }

  private stepAbort() {
    this.router.navigate(['/home']);
  }

  private stepFather() {
    this.nextText = "Next"
    this.prevText = "Cancel"
  }

  private stepChildren() {
    this.nextText = "Submit"
    this.prevText = "Previous"
  }

  private stepFinish() {
    // TODO: Client-side validation?
    this.familyService.createFamily(this.input)
      .subscribe(family => this.router.navigate([`/family/${family.id}`]))
  }

  onSubmit() {
    console.log("onSubmit called:", this.step)
  }
}
