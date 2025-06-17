import { Component, OnInit } from '@angular/core';
import { StageComponent } from './stage/stage.component';
import { CommonModule } from '@angular/common';
import { Stage } from '../../../../models/stage';
import { BoardService } from '../../../../service/board.service';
import { SessionService } from '../../../../service/session.service';
import { Card } from '../../../../models/card';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { StageCreateComponent } from '../../stage-create/stage-create.component';

@Component({
  selector: 'app-board',
  imports: [StageComponent, CommonModule],
  templateUrl: './board.component.html',
  styleUrl: './board.component.scss'
})
export class BoardComponent implements OnInit {

  constructor(private boardService: BoardService, private sessionService: SessionService, private modalService: NgbModal) { }

  stageList: Stage[] = [];
  cardList: Card[] = [];
  selectedCompany: String | null = null;
  flowId: String | null = null;

  ngOnInit(): void {
    this.sessionService.companyChanges$.subscribe(companyId => {
      this.selectedCompany = companyId;
      this.stageList = [];
      this.flowId = null;
      this.tryLoadBoard();
    });

    this.sessionService.flowChanges$.subscribe(flow => {
      this.flowId = flow;
      this.tryLoadBoard();
    });
  }    

  private tryLoadBoard() {
    if (this.selectedCompany && this.flowId) {
      console.log(this.stageList)
      this.boardService.loadBoard(Number(this.selectedCompany), Number(this.flowId)).subscribe(board => {
        this.stageList = board.stages.sort((a, b) => a.position - b.position);
        console.log('Stages loaded by boardcomp:', this.stageList)
        this.cardList = board.cards.sort((a, b) => a.position - b.position);
        console.log('Cards loaded by boardcomp:', this.cardList)
      });
    }
  }

  launchCreateStage() {
    console.log("launchCreateStage");
    const modalRef = this.modalService.open(StageCreateComponent);
  }
}