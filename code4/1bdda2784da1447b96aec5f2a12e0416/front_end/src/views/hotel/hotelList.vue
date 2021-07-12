<template>
 <div>
 <!--2020-06-26 add by zheng begin-->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="8" :sm="24">
            <a-form-item label="酒店名称">
              <a-input v-model="queryHotelInfo.hotelName" placeholder="请输入酒店名称"/>
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="24">
            <a-form-item label="房间类型" >
                <a-select  placeholder="请选择" v-model="queryHotelInfo.roomType"  default-value="BigBed" >   <!--v-model="hotel.type"-->
                  <a-select-option value="BigBed">大床房</a-select-option>
                  <a-select-option value="DoubleBed">双床房</a-select-option>
                  <a-select-option value="Family">家庭房</a-select-option>
                </a-select>
            </a-form-item>
          </a-col>
          <template v-if="advanced">
             <a-clo :md="8" :sm="24">
              <a-form-item label="原始价格区间">
               <a-select  placeholder="请选择"  v-model="queryHotelInfo.price" default-value="one" >    <!--v-model="hotel.price"-->
                  <a-select-option value="one">100-300</a-select-option>
                  <a-select-option value="tow">300-500</a-select-option>
                  <a-select-option value="there">500以上</a-select-option>
                </a-select>
              </a-form-item>
            </a-clo>
            <a-clo :md="8" :sm="24">
              <a-form-item label="房间数量" >
                <a-input-number  v-model="queryHotelInfo.curNum" placeholder="0" style="width: 100%"/>
              </a-form-item>
            </a-clo>
            <a-col :md="8" :sm="24">
              <a-form-item label="入住日期">
                <a-date-picker v-model="queryHotelInfo.beginDate" style="width: 100%" placeholder="请选择入住日期"/>
              </a-form-item>
            </a-col>
              <a-col :md="8" :sm="24">
              <a-form-item label="退房日期">
                <a-date-picker v-model="queryHotelInfo.endDate" style="width: 100%" placeholder="请选择退房日期"/>
              </a-form-item>
            </a-col>
              <a-col :md="8" :sm="24">
              <a-form-item label="酒店星级">
              <a-select default-value="Three" v-model="queryHotelInfo.hotelStar"  placeholder="请选择" style="width:120%" >
                <a-select-option value="Three">三星级</a-select-option>
                <a-select-option value="Four">四星级</a-select-option>
                <a-select-option value="Five">五星级</a-select-option>
              </a-select>
            </a-form-item>
            </a-col>
            <a-col :md="8" :sm="20">
              <a-form-item label="预定状态">
                <a-select  v-model="queryHotelInfo.useStatus" placeholder="请选择" style="width: 100%" default-value="0">
                  <a-select-option value="0">全部</a-select-option>
                  <a-select-option value="1">已预订</a-select-option>
                  <a-select-option value="2">未预定</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
          </template>
          <a-col :md="!advanced && 8 || 24" :sm="24">
            <span class="table-page-search-submitButtons" :style="advanced && { float: 'right', overflow: 'hidden' } || {} ">
             <!--  <a-button type="primary" @click="$refs.table.refresh(true)">搜索</a-button> -->
               <a-button type="primary" @click="resetSearchForm(queryHotelInfo)">搜索</a-button> 
              <a-button style="margin-left: 8px" @click="() => hotel = {}">重置</a-button>
              <a @click="toggleAdvanced" style="margin-left: 8px">
                {{ advanced ? '隐藏' : '更多' }}
                <a-icon :type="advanced ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
   <!--2020-06-26 add by zheng end-->
  <div class="hotelList"  > <!--class="table-operator"-->
    <a-layout>
        <a-layout-content style="min-width: 80%">
          <a-spin :spinning="hotelListLoading">
            <div class="card-wrapper">
                <HotelCard :hotel="item" v-for="item in hotelList" :key="item.index" @click.native="jumpToDetails(item.id)"></HotelCard>
                <div v-for="item in emptyBox" :key="item.name" class="emptyBox ant-col-xs-7 ant-col-lg-5 ant-col-xxl-3">
                </div>
              
            </div>
          </a-spin>
      </a-layout-content>
        <a-pagination showQuickJumper :total="hotelList.totalElements" :defaultCurrent="1" @change="pageChange"></a-pagination>
    </a-layout>
    
  </div>
 </div>
</template>
<script>
import HotelCard from './components/hotelCard'
import { mapGetters, mapActions, mapMutations } from 'vuex'
import {getHotelInfoAPI,getHotelsAPI} from '@/api/hotel'

export default {
  name: 'home',
  components: {
    HotelCard
  },
  data(){
    return{
      emptyBox: [{ name: 'box1' }, { name: 'box2'}, {name: 'box3'}],
        // 高级搜索 展开/关闭
      advanced: true,
        // 查询参数
      queryHotelInfo: { hotelName:"",roomType:"BigBed",price:"one",curNum:0,hotelStar:"Three",useStatus:"0"},
      hotelList:{}
    }
  },
  async mounted() {
    await this.getHotelList()
    await getHotelsAPI().then(res=>{
      this.hotelList =res;
    })
  },
  computed: {
    ...mapGetters([
      'hotelList',
      'hotelListLoading'
    ])
  },
  methods: {
    ...mapMutations([
      'set_hotelListParams',
      'set_hotelListLoading'
    ]),
    ...mapActions([
      'getHotelList'
    ]),

    pageChange(page, pageSize) {
      const data = {
        pageNo: page - 1
      }
      this.set_hotelListParams(data)
      this.set_hotelListLoading(true)
      this.getHotelList()
    },
    jumpToDetails(id){
      this.$router.push({ name: 'hotelDetail', params: { hotelId: id }})
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
    },
     resetSearchForm () {
         getHotelInfoAPI(this.queryHotelInfo).then(res=>{
           
           if(res.length !=0){

              this.hotelList=res;
           }else{
             alert("查询无数据");  
           }

         })
     
    }
  }
}
</script>
<style scoped lang="less">
  .hotelList {
    text-align: center;
    padding: 50px 0;
    .emptyBox {
      height: 0;
      margin: 10px 10px
    }
    .card-wrapper{
      display: flex;
      justify-content: space-around;
      flex-wrap: wrap;
      flex-grow: 3;
      min-height: 80%
    }
    .card-wrapper .card-item {
      margin: 30px;
      position: relative;
      height: 188px;
    }
  }
</style>