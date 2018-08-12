import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FatherDetailComponent } from './father-detail.component';

describe('FatherDetailComponent', () => {
  let component: FatherDetailComponent;
  let fixture: ComponentFixture<FatherDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FatherDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FatherDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
