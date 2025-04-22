package com.example.finalwork.model;

import java.util.List;

public class GetDiscernResultResponce {//获取图像识别结果的响应实体
    /*
    log_id
    result_num  2(结果数)
    result  [{"score":0.028031,"root":"商品-穿戴","keyword":"脖套"},{"score":0.22347,"root":"商品-五金","keyword":"拉链"}]
     */
    private long log_id;
    private int result_num;
    private List<ResultBean> result;//列表
    //
    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }
    //
    public int getResult_num() {
        return result_num;
    }

    public void setResult_num(int result_num) {
        this.result_num = result_num;
    }
    //
    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean{
        /*
        score:0.028031 可能性、相似度
        root:商品-穿戴  所属大类
        baike_info:基本信息(百科)
        keyword:脖套  名称
         */
        private double score;
        private String root;
        private BaikeInfoBean baike_info;
        private String keyword;
        //score
        public double getScore() {
            return score;
        }
        public void setScore(double score) {
            this.score = score;
        }
        //root
        public String getRoot() {
            return root;
        }
        public void setRoot(String root) {
            this.root = root;
        }
        //baike_info
        public BaikeInfoBean getBaike_info() {
            return baike_info;
        }
        public void setBaike_info(BaikeInfoBean baike_info) {
            this.baike_info = baike_info;
        }
        //keyword
        public String getKeyword() {
            return keyword;
        }
        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public static class BaikeInfoBean{
            /*
             baike_url : http://baike.baidu.com/item/%E6%96%B0%E5%9E%A3%E7%BB%93%E8%A1%A3/8035884
             image_url : http://imgsrc.baidu.com/baike/pic/item/91ef76c6a7efce1b27893518a451f3deb58f6546.jpg
             description : 新垣结衣(Aragaki Yui)，1988年6月11日出生于冲绳县那霸市。日本女演员、歌手、模特。
             */
            private String baike_url;
            private String image_url;
            private String description;
            //baike_url
            public String getBaike_url() {
                return baike_url;
            }
            public void setBaike_url(String baike_url) {
                this.baike_url = baike_url;
            }
            //image_url
            public String getImage_url() {
                return image_url;
            }
            public void setImage_url(String image_url) {
                this.image_url = image_url;
            }
            //description
            public String getDescription() {
                return description;
            }
            public void setDescription(String description) {
                this.description = description;
            }
        }
    }
}
