import { EfficiencyResponse } from "./EfficiencyResponse";
import { LeadTimeResponse } from "./LeadTimeResponse";
import { ThroughputResponse } from "./ThroughputResponse";
import { QueuesResponse } from "./QueuesResponse";

export interface MetricsResponse {
  leadTime: LeadTimeResponse;
  throughput: ThroughputResponse;
  efficiency: EfficiencyResponse;
  queue: QueuesResponse;
}