import { Component, OnInit } from '@angular/core';

import { FamilyService } from '../../services/family.service';

import { Family } from '../../models/family.model';
import { Child } from '../../models/child.model';

@Component({
  selector: 'app-family-search',
  templateUrl: './family-search.component.html'
})
export class FamilySearchComponent implements OnInit {

  // Use the Child model as an intermediary to store
  // search parameters, following query-by-example principle.
  input: Child = {
    firstName: "",
    secondName: "",
    birthDate: "",
    pesel: "",
    sex: null
  } as Child;

  searchResult: Family[];

  constructor(
    private familyService: FamilyService
  ) {}

  ngOnInit() {
  }

  private valueOrNull(value: string): string {
    if (value === null || value === undefined) {
      return null;
    }

    value = value.trim();
    return value.length === 0 ? null : value;
  }

  performSearch() {
    this.searchResult = null;

    this.input.firstName = this.valueOrNull(this.input.firstName);
    this.input.secondName = this.valueOrNull(this.input.secondName);
    this.input.pesel = this.valueOrNull(this.input.pesel);
    this.input.birthDate = this.valueOrNull(this.input.birthDate);
    this.input.sex = this.valueOrNull(this.input.sex);

    this.familyService.searchFamily(this.input)
      .subscribe(families => this.searchResult = families);
  }
}
