import { TestBed } from '@angular/core/testing';

import { SecretariatRouterService } from './secretariat-router.service';

describe('SecretariatRouterService', () => {
  let service: SecretariatRouterService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SecretariatRouterService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
