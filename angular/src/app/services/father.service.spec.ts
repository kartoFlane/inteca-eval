import { TestBed, inject } from '@angular/core/testing';

import { FatherService } from './father.service';

describe('FatherService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [FatherService]
    });
  });

  it('should be created', inject([FatherService], (service: FatherService) => {
    expect(service).toBeTruthy();
  }));
});
