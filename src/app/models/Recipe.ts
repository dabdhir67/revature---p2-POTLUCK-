import { Chef } from "./Chef";

export class Recipe {
    
    id:number;
    title:string;
    body:string;
    date:Date;
    chef:Chef;


    constructor(id: number, title:string, body:string, date:Date, chef:Chef){
        this.id=id;
        this.title=title;
        this.body=body;
        this.date=date;
        this.chef=chef;
    }
}
