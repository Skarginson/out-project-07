export type StudentType = 'FAT' | 'FISE' | 'FISA';

export type HumorType = 'CAFE_ADDICT' | 'NOCTAMBULE' | 'FANTOME' | 'GRIND_MASTER';

export interface Stats {
  hp: number;
  attack: number;
  defense: number;
}

export interface Student {
  id?: number;
  firstName: string;
  lastName: string;
  nickname?: string;
  speciality?: string;
  type: StudentType;
  humorType?: HumorType;
  stats?: Stats;
  superPower?: string;
  catchPhrase?: string;
  imageUrl?: string;
  createdAt?: string;
}
