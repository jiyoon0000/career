export type StudyRoom = {
    id: number;
    name: string;
    region: string;
    payType: string;
    category: string;
    imageUrl: string;
  };
  
  export type StudyRoomDetail = StudyRoom & {
    id: number;
    name: string;
    category: string;
    region: string;
    payType: string;
    imageUrl: string;
    intro: string;
    openingHours: string;
    address: string;
    contact: string;
    serviceUrl: string;
    organizationName: string;
    x: number;
    y: number;
  };
  