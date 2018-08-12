import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { HttpClientInMemoryWebApiModule } from 'angular-in-memory-web-api';
import { InMemoryDataService } from './services/in-memory-data.service';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';

import { HeaderComponent } from './components/header/header.component';
import { HomeComponent } from './components/home/home.component';
import { FamilyDetailComponent } from './components/family-detail/family-detail.component';
import { FatherDetailComponent } from './components/father-detail/father-detail.component';
import { ChildDetailComponent } from './components/child-detail/child-detail.component';
import { FamilySearchComponent } from './components/family-search/family-search.component';
import { FamilyAddComponent } from './components/family-add/family-add.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HomeComponent,
    FamilyDetailComponent,
    FatherDetailComponent,
    ChildDetailComponent,
    FamilySearchComponent,
    FamilyAddComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,

    // In-memory web API used in development for dry testing.
    /*
    HttpClientInMemoryWebApiModule.forRoot(
      InMemoryDataService, { dataEncapsulation: false }
    ),
    */


    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
