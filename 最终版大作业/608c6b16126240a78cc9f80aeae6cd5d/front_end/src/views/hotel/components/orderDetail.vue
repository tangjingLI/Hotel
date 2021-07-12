<template>
    <a-modal
            :visible="orderDetailVisible"
            title="订单详情"
            cancelText="关闭"
            @cancel="shut"
            okText="确定"
            @ok="shut"
    >

        <a-form style="margin-top: 30px" v-bind="formItemLayout">
            <a-form-item v-bind="formItemLayout" label="酒店名称">
                <span v-text="checkCurOrder.hotelName"></span>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="下单日期">
                <span>{{checkCurOrder.createDate}}</span>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="房型信息">
                <span v-if="checkCurOrder.roomType == 'BigBed'">大床房</span>
                <span v-if="checkCurOrder.roomType == 'DoubleBed'">双床房</span>
                <span v-if="checkCurOrder.roomType == 'Family'">家庭房</span>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="入住人姓名">
                <span>{{checkCurOrder.clientName}}</span>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="手机号">
                <span>{{ checkCurOrder.phoneNumber }}</span>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="入住日期">
                <span>{{checkCurOrder.checkInDate }}</span>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="入住人数">
                <span>{{checkCurOrder.peopleNum }}</span>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="有无儿童">
                <span v-if="checkCurOrder.haveChild == false">无</span>
                <span v-if="checkCurOrder.haveChild == true">有</span>

            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="房间数">
                <span>{{checkCurOrder.roomNum }}</span>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="订单总价">
                <span>{{ checkCurOrder.price }}</span>
            </a-form-item>
        </a-form>
    </a-modal>
</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'

    export default {
        name: 'orderDet',
        data() {
            return {
                formItemLayout: {
                    labelCol: {
                        xs: { span: 12 },
                        sm: { span: 6 },
                    },
                    wrapperCol: {
                        xs: { span: 24 },
                        sm: { span: 16 },
                    },
                },

            }
        },

        computed: {
            ...mapGetters([
                'orderDetailVisible',
                'userId',
                'userInfo',
                'userOrderList',
                'currentOrderRoom',
                'checkCurOrder',
            ]),
        },

        async mounted() {
            await this.getUserInfo();
            await this.getUserOrders();

        },

        methods: {
            ...mapMutations([
                'set_orderDetailVisible',
                'set_userOrderList',
                'set_userInfo',

            ]),
            ...mapActions([
                'getUserInfo',
                'getOrder',


            ]),
            shut() {
                this.set_orderDetailVisible(false)
            },
        }
    }
</script>


<style scoped>

</style>