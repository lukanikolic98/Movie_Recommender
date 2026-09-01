import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecommendMoviesComponent } from './movie-recommendation';

describe('MovieRecommendation', () => {
  let component: RecommendMoviesComponent;
  let fixture: ComponentFixture<RecommendMoviesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RecommendMoviesComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(RecommendMoviesComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
