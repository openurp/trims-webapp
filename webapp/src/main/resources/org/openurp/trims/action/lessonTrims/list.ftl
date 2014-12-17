[@b.head/]
[#assign base=request.contextPath/]
<div id="lessonTrimsChart" style="height:300px;">

</div>
<script src="${base}/static/js/echarts/echarts-all.js"></script>
<script type="text/javascript">
        $(function () {
            // 基于准备好的dom，初始化echarts图表
            var myChart = echarts.init(document.getElementById('lessonTrimsChart')); 
            
            var option = {
                title: {text:'[#if year??]${year}学年[/#if][#if term??]  第${term}学期[/#if] 按开课数量统计',  subtext : '点击图表显示某院系按学期统计'},
                //renderAsImage:true,
                tooltip: {
                    show: true
                },
                xAxis : [
                    {
                        name : '院系',
                        type : 'category',
                        axisLabel:{interval:0, rotate:-25},
                        data : [[#list datas as d][#if d_index gt 0],[/#if]'${dempartmentMap[d[0]?string]}'[/#list]]
                    }
                ],
                yAxis : [
                    {
                        name : '开课数量',
                        type : 'value'
                    }
                ],
                series : [
                    {
                        "type":"bar",
                        barMinHeight: 20,
                        itemStyle: {
                            normal: {
                                color: function(params) {
                                    // build a color map as your need.
                                    var colorList = [
                                      '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
                                       '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
                                       '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
                                    ];
                                    return colorList[params.dataIndex%colorList.length]
                                },
                                label: {
                                    show: true,
                                    position: 'top',
                                    formatter: '{c}'
                                }
                            }
                        },
                        "data":[[#list datas as d][#if d_index gt 0],[/#if]${d[1]}[/#list]],
                        markLine : {
                            data : [
                                {type : 'average', name: '平均值'}
                            ]
                        }
                    }
                ]
            };
            var departmentIds = [[#list datas as d][#if d_index gt 0],[/#if]${d[0]}[/#list]]
            // 为echarts对象加载数据 
            myChart.setOption(option); 
            myChart.on('click', function (param){
              bg.Go('${b.url('!department')}?did='+departmentIds[param.dataIndex],'LessonDepartmentChartDiv')
            });
        });
</script>
[@b.div id="LessonDepartmentChartDiv"/]
[@b.foot/]