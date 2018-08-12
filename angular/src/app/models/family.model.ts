import { Father } from './father.model';
import { Child } from './child.model';

export class Family {
  constructor(
    public id: number,
    public father: Father,
    public children: Child[]
  ) {
  }
}
