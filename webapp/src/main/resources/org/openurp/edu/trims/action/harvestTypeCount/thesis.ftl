[@b.head/]
  [#include "../echarts.ftl"/]
[#if datas?size gt 0]
  [#assign title][@beginAndEnd/]  论文按成果类型统计[/#assign]
  [@echarts id="thesis_chart" title=title maxAndMin=false 
    xname='成果类型' yname='数量'
    names=names values=values/]
[#else]
<div style="padding:50px; font-size:20px; text-align:center">暂无论文成果数据</div>
[/#if]
  [@b.div id="harvestTypeDiv"/]
  [@b.div href="!literature?beginYear=${beginYear!}&endYear=${endYear!}"/]
[@b.foot/]