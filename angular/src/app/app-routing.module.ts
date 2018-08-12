import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { HomeComponent } from './components/home/home.component'
import { FamilyDetailComponent } from './components/family-detail/family-detail.component'
import { FatherDetailComponent } from './components/father-detail/father-detail.component'
import { ChildDetailComponent } from './components/child-detail/child-detail.component'
import { FamilyAddComponent } from './components/family-add/family-add.component'
import { FamilySearchComponent } from './components/family-search/family-search.component'

const routes: Routes = [
  { path: "", redirectTo: "/home", pathMatch: "full" },
  { path: "home", component: HomeComponent, pathMatch: "full" },

  { path: "family/new", component: FamilyAddComponent, pathMatch: "full" },
  { path: "family/search", component: FamilySearchComponent, pathMatch: "full" },

  { path: "family/:id", component: FamilyDetailComponent, pathMatch: "full" },
  { path: "father/:id", component: FatherDetailComponent, pathMatch: "full" },
  { path: "child/:id", component: ChildDetailComponent, pathMatch: "full" }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule { }
