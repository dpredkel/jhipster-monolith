import { Moment } from 'moment';
import { ITag } from 'app/shared/model/tag.model';

export interface IEntry {
  id?: number;
  title?: string;
  content?: any;
  date?: string;
  blogName?: string;
  blogId?: number;
  tags?: ITag[];
}

export const defaultValue: Readonly<IEntry> = {};
