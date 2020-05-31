package com.wind.music.bean;

import java.util.List;

/**
 * Created by Wind on 2017/8/19.
 */

public class BillBoardBean extends BaseBean {

    /**
     * song_list : [{"artist_id":"672865435","language":"国语","pic_big":"http://qukufile2.qianqian.com/data2/pic/b733a1a9fc0f63c7015be29b7b840b66/672866107/672866107.jpg@s_2,w_150,h_150","pic_small":"http://qukufile2.qianqian.com/data2/pic/b733a1a9fc0f63c7015be29b7b840b66/672866107/672866107.jpg@s_2,w_90,h_90","country":"内地","area":"0","publishtime":"2019-12-16","album_no":"1","lrclink":"http://qukufile2.qianqian.com/data2/lrc/4589294157b56806f6ff597660c84329/672866365/672866365.txt","copy_type":"1","hot":"185951","all_artist_ting_uid":"340615848","resource_type":"0","is_new":"1","rank_change":"0","rank":"1","all_artist_id":"672865435","style":"","del_status":"0","relate_status":"0","toneid":"0","all_rate":"96,128,224,320,flac","file_duration":175,"has_mv_mobile":0,"versions":"","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","biaoshi":"lossless,perm-1","info":"","has_filmtv":"0","si_proxycompany":"北京万上文化传媒有限公司","res_encryption_flag":"0","song_id":"672865438","title":"桥边姑娘","ting_uid":"340615848","author":"舞蹈女神诺涵","album_id":"672865436","album_title":"桥边姑娘","is_first_publish":0,"havehigh":2,"charge":0,"has_mv":0,"learn":0,"song_source":"web","piao_id":"0","korean_bb_song":"0","resource_type_ext":"0","mv_provider":"0000000000","artist_name":"舞蹈女神诺涵","pic_radio":"http://qukufile2.qianqian.com/data2/pic/b733a1a9fc0f63c7015be29b7b840b66/672866107/672866107.jpg@s_2,w_300,h_300","pic_s500":"http://qukufile2.qianqian.com/data2/pic/b733a1a9fc0f63c7015be29b7b840b66/672866107/672866107.jpg@s_2,w_500,h_500","pic_premium":"http://qukufile2.qianqian.com/data2/pic/b733a1a9fc0f63c7015be29b7b840b66/672866107/672866107.jpg@s_2,w_500,h_500","pic_huge":"http://qukufile2.qianqian.com/data2/pic/b733a1a9fc0f63c7015be29b7b840b66/672866107/672866107.jpg","album_500_500":"http://qukufile2.qianqian.com/data2/pic/b733a1a9fc0f63c7015be29b7b840b66/672866107/672866107.jpg@s_2,w_500,h_500","album_800_800":"","album_1000_1000":"http://qukufile2.qianqian.com/data2/pic/b733a1a9fc0f63c7015be29b7b840b66/672866107/672866107.jpg"},{"artist_id":"674713815","language":"国语","pic_big":"http://qukufile2.qianqian.com/data2/pic/8b1aab6be81f10639c01c1401a20463c/675021896/675021896.jpg@s_2,w_150,h_150","pic_small":"http://qukufile2.qianqian.com/data2/pic/8b1aab6be81f10639c01c1401a20463c/675021896/675021896.jpg@s_2,w_90,h_90","country":"内地","area":"0","publishtime":"2020-03-24","album_no":"2","lrclink":"http://qukufile2.qianqian.com/data2/lrc/cc8492076ab0def9daf88daf9a9b3751/675022268/675022268.txt","copy_type":"1","hot":"133785","all_artist_ting_uid":"340642446","resource_type":"0","is_new":"1","rank_change":"1","rank":"2","all_artist_id":"674713815","style":"","del_status":"0","relate_status":"0","toneid":"0","all_rate":"96,224,128,320,flac","file_duration":236,"has_mv_mobile":0,"versions":"","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","biaoshi":"lossless,perm-1","info":"","has_filmtv":"0","si_proxycompany":"labelname_北京欣然文化传媒有限公司","res_encryption_flag":"0","song_id":"675017674","title":"少年（童声版）","ting_uid":"340642446","author":"宋小睿","album_id":"675017666","album_title":"少年","is_first_publish":0,"havehigh":2,"charge":0,"has_mv":0,"learn":0,"song_source":"web","piao_id":"0","korean_bb_song":"0","resource_type_ext":"0","mv_provider":"0000000000","artist_name":"宋小睿","pic_radio":"http://qukufile2.qianqian.com/data2/pic/8b1aab6be81f10639c01c1401a20463c/675021896/675021896.jpg@s_2,w_300,h_300","pic_s500":"http://qukufile2.qianqian.com/data2/pic/8b1aab6be81f10639c01c1401a20463c/675021896/675021896.jpg@s_2,w_500,h_500","pic_premium":"http://qukufile2.qianqian.com/data2/pic/8b1aab6be81f10639c01c1401a20463c/675021896/675021896.jpg@s_2,w_500,h_500","pic_huge":"http://qukufile2.qianqian.com/data2/pic/8b1aab6be81f10639c01c1401a20463c/675021896/675021896.jpg","album_500_500":"http://qukufile2.qianqian.com/data2/pic/8b1aab6be81f10639c01c1401a20463c/675021896/675021896.jpg@s_2,w_500,h_500","album_800_800":"","album_1000_1000":"http://qukufile2.qianqian.com/data2/pic/8b1aab6be81f10639c01c1401a20463c/675021896/675021896.jpg"},{"artist_id":"123446035","language":"国语","pic_big":"http://qukufile2.qianqian.com/data2/pic/0d359ec1be6f5365f92d4c83d3eeb022/603758238/603758238.jpg@s_2,w_150,h_150","pic_small":"http://qukufile2.qianqian.com/data2/pic/0d359ec1be6f5365f92d4c83d3eeb022/603758238/603758238.jpg@s_2,w_90,h_90","country":"内地","area":"0","publishtime":"2016-05-20","album_no":"1","lrclink":"http://qukufile2.qianqian.com/data2/lrc/7a07a153b8a9a9d918bbee676b7d4118/603149730/603149730.lrc","copy_type":"1","hot":"180640","all_artist_ting_uid":"164528737","resource_type":"0","is_new":"0","rank_change":"1","rank":"3","all_artist_id":"123446035","style":"流行","del_status":"0","relate_status":"0","toneid":"0","all_rate":"96,128,224,320,flac","file_duration":313,"has_mv_mobile":0,"versions":"","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","biaoshi":"lossless,perm-1","info":"动画电影《大鱼海棠》印象曲","has_filmtv":"0","si_proxycompany":"霍尔果斯青春光线影业有限公司","res_encryption_flag":"0","song_id":"265715650","title":"大鱼","ting_uid":"164528737","author":"周深","album_id":"265715651","album_title":"大鱼","is_first_publish":0,"havehigh":2,"charge":0,"has_mv":0,"learn":0,"song_source":"web","piao_id":"0","korean_bb_song":"0","resource_type_ext":"0","mv_provider":"0000000000","artist_name":"周深","pic_radio":"http://qukufile2.qianqian.com/data2/pic/0d359ec1be6f5365f92d4c83d3eeb022/603758238/603758238.jpg@s_2,w_300,h_300","pic_s500":"http://qukufile2.qianqian.com/data2/pic/0d359ec1be6f5365f92d4c83d3eeb022/603758238/603758238.jpg@s_2,w_500,h_500","pic_premium":"http://qukufile2.qianqian.com/data2/pic/0d359ec1be6f5365f92d4c83d3eeb022/603758238/603758238.jpg@s_2,w_500,h_500","pic_huge":"http://qukufile2.qianqian.com/data2/pic/0d359ec1be6f5365f92d4c83d3eeb022/603758238/603758238.jpg","album_500_500":"http://qukufile2.qianqian.com/data2/pic/0d359ec1be6f5365f92d4c83d3eeb022/603758238/603758238.jpg@s_2,w_500,h_500","album_800_800":"","album_1000_1000":"http://qukufile2.qianqian.com/data2/pic/0d359ec1be6f5365f92d4c83d3eeb022/603758238/603758238.jpg"},{"artist_id":"88","language":"国语","pic_big":"http://qukufile2.qianqian.com/data2/pic/49e6161afb13e3eda9d1cb4e304561a2/676820947/676820947.jpg@s_2,w_150,h_150","pic_small":"http://qukufile2.qianqian.com/data2/pic/49e6161afb13e3eda9d1cb4e304561a2/676820947/676820947.jpg@s_2,w_90,h_90","country":"内地","area":"0","publishtime":"2016-07-18","album_no":"4","lrclink":"http://qukufile2.qianqian.com/data2/lrc/bed1fcb36f51259eefab8ba6d95f524f/672457403/672457403.lrc","copy_type":"1","hot":"138090","all_artist_ting_uid":"2517","resource_type":"0","is_new":"0","rank_change":"-2","rank":"4","all_artist_id":"88","style":"流行","del_status":"0","relate_status":"0","toneid":"0","all_rate":"96,128,224,320,flac","file_duration":261,"has_mv_mobile":0,"versions":"","bitrate_fee":"{\"0\":\"129|-1\",\"1\":\"-1|-1\"}","biaoshi":"lossless,vip,perm-1","info":"","has_filmtv":"0","si_proxycompany":"华宇世博音乐文化（北京）有限公司-普通代理","res_encryption_flag":"0","song_id":"242078437","title":"演员","ting_uid":"2517","author":"薛之谦","album_id":"241838068","album_title":"初学者","is_first_publish":0,"havehigh":2,"charge":0,"has_mv":0,"learn":0,"song_source":"web","piao_id":"0","korean_bb_song":"0","resource_type_ext":"2","mv_provider":"0000000000","artist_name":"薛之谦","pic_radio":"http://qukufile2.qianqian.com/data2/pic/49e6161afb13e3eda9d1cb4e304561a2/676820947/676820947.jpg@s_2,w_300,h_300","pic_s500":"http://qukufile2.qianqian.com/data2/pic/49e6161afb13e3eda9d1cb4e304561a2/676820947/676820947.jpg@s_2,w_500,h_500","pic_premium":"http://qukufile2.qianqian.com/data2/pic/49e6161afb13e3eda9d1cb4e304561a2/676820947/676820947.jpg@s_2,w_500,h_500","pic_huge":"http://qukufile2.qianqian.com/data2/pic/49e6161afb13e3eda9d1cb4e304561a2/676820947/676820947.jpg@s_2,w_1000,h_1000","album_500_500":"http://qukufile2.qianqian.com/data2/pic/49e6161afb13e3eda9d1cb4e304561a2/676820947/676820947.jpg@s_2,w_500,h_500","album_800_800":"","album_1000_1000":"http://qukufile2.qianqian.com/data2/pic/49e6161afb13e3eda9d1cb4e304561a2/676820947/676820947.jpg@s_2,w_1000,h_1000"},{"artist_id":"672906440","language":"国语","pic_big":"http://qukufile2.qianqian.com/data2/pic/822e11b209903ff44d67fa5148e01a84/672906473/672906473.jpg@s_2,w_150,h_150","pic_small":"http://qukufile2.qianqian.com/data2/pic/822e11b209903ff44d67fa5148e01a84/672906473/672906473.jpg@s_2,w_90,h_90","country":"内地","area":"0","publishtime":"2019-12-18","album_no":"1","lrclink":"http://qukufile2.qianqian.com/data2/lrc/bc98479f910e9d8f2fa25460aa90be84/672906575/672906575.txt","copy_type":"1","hot":"83162","all_artist_ting_uid":"340616296","resource_type":"0","is_new":"1","rank_change":"1","rank":"5","all_artist_id":"672906440","style":"","del_status":"0","relate_status":"0","toneid":"0","all_rate":"96,128,224,320,flac","file_duration":182,"has_mv_mobile":0,"versions":"","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","biaoshi":"lossless,perm-1","info":"","has_filmtv":"0","si_proxycompany":"北京万上文化传媒有限公司","res_encryption_flag":"0","song_id":"672906443","title":"桥边姑娘","ting_uid":"340616296","author":"垂耳兔","album_id":"672906441","album_title":"桥边姑娘","is_first_publish":0,"havehigh":2,"charge":0,"has_mv":0,"learn":0,"song_source":"web","piao_id":"0","korean_bb_song":"0","resource_type_ext":"0","mv_provider":"0000000000","artist_name":"垂耳兔","pic_radio":"http://qukufile2.qianqian.com/data2/pic/822e11b209903ff44d67fa5148e01a84/672906473/672906473.jpg@s_2,w_300,h_300","pic_s500":"http://qukufile2.qianqian.com/data2/pic/822e11b209903ff44d67fa5148e01a84/672906473/672906473.jpg@s_2,w_500,h_500","pic_premium":"http://qukufile2.qianqian.com/data2/pic/822e11b209903ff44d67fa5148e01a84/672906473/672906473.jpg@s_2,w_500,h_500","pic_huge":"http://qukufile2.qianqian.com/data2/pic/822e11b209903ff44d67fa5148e01a84/672906473/672906473.jpg","album_500_500":"http://qukufile2.qianqian.com/data2/pic/822e11b209903ff44d67fa5148e01a84/672906473/672906473.jpg@s_2,w_500,h_500","album_800_800":"","album_1000_1000":"http://qukufile2.qianqian.com/data2/pic/822e11b209903ff44d67fa5148e01a84/672906473/672906473.jpg"},{"artist_id":"13911340","language":"国语","pic_big":"http://qukufile2.qianqian.com/data2/pic/106124457/106124457.jpg@s_2,w_150,h_150","pic_small":"http://qukufile2.qianqian.com/data2/pic/106124457/106124457.jpg@s_2,w_90,h_90","country":"内地","area":"0","publishtime":"2013-12-06","album_no":"2","lrclink":"http://qukufile2.qianqian.com/data2/lrc/1608f69676398d1545d7e03ac52bb350/603149882/603149882.lrc","copy_type":"1","hot":"274737","all_artist_ting_uid":"2319312","resource_type":"0","is_new":"0","rank_change":"-1","rank":"6","all_artist_id":"13911340","style":"流行","del_status":"0","relate_status":"0","toneid":"0","all_rate":"96,128,224,320,flac","file_duration":125,"has_mv_mobile":0,"versions":"","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","biaoshi":"lossless,perm-1","info":"","has_filmtv":"0","si_proxycompany":"北京惠达州文化传播有限公司","res_encryption_flag":"0","song_id":"106125582","title":"逆流成河","ting_uid":"2319312","author":"金南玲","album_id":"106125580","album_title":"来生","is_first_publish":0,"havehigh":2,"charge":0,"has_mv":0,"learn":1,"song_source":"web","piao_id":"0","korean_bb_song":"0","resource_type_ext":"0","mv_provider":"0000000000","artist_name":"金南玲","pic_radio":"http://qukufile2.qianqian.com/data2/pic/106124457/106124457.jpg@s_2,w_300,h_300","pic_s500":"http://qukufile2.qianqian.com/data2/pic/106124457/106124457.jpg@s_2,w_500,h_500","pic_premium":"http://qukufile2.qianqian.com/data2/pic/106124457/106124457.jpg@s_2,w_500,h_500","pic_huge":"http://qukufile2.qianqian.com/data2/pic/106124457/106124457.jpg@s_2,w_1000,h_1000","album_500_500":"http://qukufile2.qianqian.com/data2/pic/106124457/106124457.jpg@s_2,w_500,h_500","album_800_800":"","album_1000_1000":"http://qukufile2.qianqian.com/data2/pic/106124457/106124457.jpg@s_2,w_1000,h_1000"},{"artist_id":"60722128","language":"国语","pic_big":"http://qukufile2.qianqian.com/data2/pic/d24656e3b055acb05fe096f781920016/675907119/675907119.jpg@s_2,w_150,h_150","pic_small":"http://qukufile2.qianqian.com/data2/pic/d24656e3b055acb05fe096f781920016/675907119/675907119.jpg@s_2,w_90,h_90","country":"内地","area":"0","publishtime":"2020-03-27","album_no":"1","lrclink":"http://qukufile2.qianqian.com/data2/lrc/b5459ebbfdea9acecf2c462e9b70ce44/675236811/675236811.txt","copy_type":"1","hot":"79082","all_artist_ting_uid":"209968568","resource_type":"0","is_new":"1","rank_change":"0","rank":"7","all_artist_id":"60722128","style":"","del_status":"0","relate_status":"0","toneid":"0","all_rate":"96,224,128,320,flac","file_duration":279,"has_mv_mobile":0,"versions":"","bitrate_fee":"{\"0\":\"129|-1\",\"1\":\"-1|-1\"}","biaoshi":"lossless,vip,perm-3","info":"","has_filmtv":"0","si_proxycompany":"华宇世博音乐文化（北京）有限公司-普通代理","res_encryption_flag":"0","song_id":"675217012","title":"拥抱春天","ting_uid":"209968568","author":"A公馆","album_id":"675217010","album_title":"拥抱春天","is_first_publish":0,"havehigh":2,"charge":0,"has_mv":1,"learn":0,"song_source":"web","piao_id":"0","korean_bb_song":"0","resource_type_ext":"2","mv_provider":"0000000000","artist_name":"A公馆","pic_radio":"http://qukufile2.qianqian.com/data2/pic/d24656e3b055acb05fe096f781920016/675907119/675907119.jpg@s_2,w_300,h_300","pic_s500":"http://qukufile2.qianqian.com/data2/pic/d24656e3b055acb05fe096f781920016/675907119/675907119.jpg@s_2,w_500,h_500","pic_premium":"http://qukufile2.qianqian.com/data2/pic/d24656e3b055acb05fe096f781920016/675907119/675907119.jpg@s_2,w_500,h_500","pic_huge":"http://qukufile2.qianqian.com/data2/pic/d24656e3b055acb05fe096f781920016/675907119/675907119.jpg@s_2,w_1000,h_1000","album_500_500":"http://qukufile2.qianqian.com/data2/pic/d24656e3b055acb05fe096f781920016/675907119/675907119.jpg@s_2,w_500,h_500","album_800_800":"","album_1000_1000":"http://qukufile2.qianqian.com/data2/pic/d24656e3b055acb05fe096f781920016/675907119/675907119.jpg@s_2,w_1000,h_1000"},{"artist_id":"762","language":"国语","pic_big":"http://qukufile2.qianqian.com/data2/pic/28e4b596b16737fbaf54c600c19947a6/672527540/672527540.jpg@s_2,w_150,h_150","pic_small":"http://qukufile2.qianqian.com/data2/pic/28e4b596b16737fbaf54c600c19947a6/672527540/672527540.jpg@s_2,w_90,h_90","country":"内地","area":"0","publishtime":"2019-11-27","album_no":"1","lrclink":"http://qukufile2.qianqian.com/data2/lrc/bd10eed4439097e0f7066a94528f7b2e/672526511/672526511.lrc","copy_type":"1","hot":"76099","all_artist_ting_uid":"1376","resource_type":"0","is_new":"1","rank_change":"1","rank":"8","all_artist_id":"762","style":"","del_status":"0","relate_status":"0","toneid":"0","all_rate":"96,224,128,320,flac","file_duration":296,"has_mv_mobile":0,"versions":"","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","biaoshi":"lossless,perm-1","info":"","has_filmtv":"0","si_proxycompany":"北京普天同乐文化传媒有限公司","res_encryption_flag":"0","song_id":"672526448","title":"记忆里的雪","ting_uid":"1376","author":"龙梅子","album_id":"672526446","album_title":"记忆里的雪","is_first_publish":0,"havehigh":2,"charge":0,"has_mv":0,"learn":0,"song_source":"web","piao_id":"0","korean_bb_song":"0","resource_type_ext":"0","mv_provider":"0000000000","artist_name":"龙梅子","pic_radio":"http://qukufile2.qianqian.com/data2/pic/28e4b596b16737fbaf54c600c19947a6/672527540/672527540.jpg@s_2,w_300,h_300","pic_s500":"http://qukufile2.qianqian.com/data2/pic/28e4b596b16737fbaf54c600c19947a6/672527540/672527540.jpg@s_2,w_500,h_500","pic_premium":"http://qukufile2.qianqian.com/data2/pic/28e4b596b16737fbaf54c600c19947a6/672527540/672527540.jpg@s_2,w_500,h_500","pic_huge":"http://qukufile2.qianqian.com/data2/pic/28e4b596b16737fbaf54c600c19947a6/672527540/672527540.jpg@s_2,w_1000,h_1000","album_500_500":"http://qukufile2.qianqian.com/data2/pic/28e4b596b16737fbaf54c600c19947a6/672527540/672527540.jpg@s_2,w_500,h_500","album_800_800":"","album_1000_1000":"http://qukufile2.qianqian.com/data2/pic/28e4b596b16737fbaf54c600c19947a6/672527540/672527540.jpg@s_2,w_1000,h_1000"},{"artist_id":"14715949","language":"国语","pic_big":"http://qukufile2.qianqian.com/data2/pic/bae766e5c670ca417cc433156efcb528/675012927/675012927.png@s_2,w_150,h_150","pic_small":"http://qukufile2.qianqian.com/data2/pic/bae766e5c670ca417cc433156efcb528/675012927/675012927.png@s_2,w_90,h_90","country":"内地","area":"0","publishtime":"2020-03-25","album_no":"1","lrclink":"http://qukufile2.qianqian.com/data2/lrc/4d34b71f289827f459aadc951b172248/675012957/675012957.txt","copy_type":"1","hot":"74470","all_artist_ting_uid":"9631389,239563102","resource_type":"0","is_new":"1","rank_change":"-1","rank":"9","all_artist_id":"14715949,267237378","style":"","del_status":"0","relate_status":"0","toneid":"0","all_rate":"96,224,128,320,flac","file_duration":290,"has_mv_mobile":0,"versions":"","bitrate_fee":"{\"0\":\"129|-1\",\"1\":\"-1|-1\"}","biaoshi":"lossless,vip,perm-1","info":"","has_filmtv":"0","si_proxycompany":"永稻星（北京）文化娱乐有限公司","res_encryption_flag":"0","song_id":"675012544","title":"山河无恙在我胸-蔡徐坤+佟丽娅","ting_uid":"9631389","author":"佟丽娅,蔡徐坤","album_id":"675012542","album_title":"山河无恙在我胸","is_first_publish":0,"havehigh":2,"charge":0,"has_mv":0,"learn":0,"song_source":"web","piao_id":"0","korean_bb_song":"0","resource_type_ext":"2","mv_provider":"0000000000","artist_name":"佟丽娅,蔡徐坤","pic_radio":"http://qukufile2.qianqian.com/data2/pic/bae766e5c670ca417cc433156efcb528/675012927/675012927.png@s_2,w_300,h_300","pic_s500":"http://qukufile2.qianqian.com/data2/pic/bae766e5c670ca417cc433156efcb528/675012927/675012927.png@s_2,w_500,h_500","pic_premium":"http://qukufile2.qianqian.com/data2/pic/bae766e5c670ca417cc433156efcb528/675012927/675012927.png@s_2,w_500,h_500","pic_huge":"http://qukufile2.qianqian.com/data2/pic/bae766e5c670ca417cc433156efcb528/675012927/675012927.png@s_2,w_1000,h_1000","album_500_500":"http://qukufile2.qianqian.com/data2/pic/bae766e5c670ca417cc433156efcb528/675012927/675012927.png@s_2,w_500,h_500","album_800_800":"","album_1000_1000":"http://qukufile2.qianqian.com/data2/pic/bae766e5c670ca417cc433156efcb528/675012927/675012927.png@s_2,w_1000,h_1000"},{"artist_id":"10388780","language":"国语","pic_big":"http://qukufile2.qianqian.com/data2/pic/58754f79bde49e3ee8fd6c5326e5a240/614336850/614336850.jpg@s_2,w_150,h_150","pic_small":"http://qukufile2.qianqian.com/data2/pic/58754f79bde49e3ee8fd6c5326e5a240/614336850/614336850.jpg@s_2,w_90,h_90","country":"内地","area":"0","publishtime":"2017-06-16","album_no":"5","lrclink":"http://qukufile2.qianqian.com/data2/lrc/c157208ccbcff03596aa1c08c246ad0f/614336922/614336922.lrc","copy_type":"1","hot":"82882","all_artist_ting_uid":"556015","resource_type":"0","is_new":"0","rank_change":"1","rank":"10","all_artist_id":"10388780","style":"","del_status":"0","relate_status":"0","toneid":"0","all_rate":"96,128,224,320,flac","file_duration":235,"has_mv_mobile":0,"versions":"影视原声","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","biaoshi":"lossless,perm-1","info":"","has_filmtv":"0","si_proxycompany":"北京听见时代娱乐传媒有限公司","res_encryption_flag":"0","song_id":"542369506","title":"追光者","ting_uid":"556015","author":"岑宁儿","album_id":"541982838","album_title":"夏至未至 电视原声带","is_first_publish":0,"havehigh":2,"charge":0,"has_mv":0,"learn":0,"song_source":"web","piao_id":"0","korean_bb_song":"0","resource_type_ext":"0","mv_provider":"0000000000","artist_name":"岑宁儿","pic_radio":"http://qukufile2.qianqian.com/data2/pic/58754f79bde49e3ee8fd6c5326e5a240/614336850/614336850.jpg@s_2,w_300,h_300","pic_s500":"http://qukufile2.qianqian.com/data2/pic/58754f79bde49e3ee8fd6c5326e5a240/614336850/614336850.jpg@s_2,w_500,h_500","pic_premium":"http://qukufile2.qianqian.com/data2/pic/58754f79bde49e3ee8fd6c5326e5a240/614336850/614336850.jpg@s_2,w_500,h_500","pic_huge":"http://qukufile2.qianqian.com/data2/pic/58754f79bde49e3ee8fd6c5326e5a240/614336850/614336850.jpg@s_2,w_1000,h_1000","album_500_500":"http://qukufile2.qianqian.com/data2/pic/58754f79bde49e3ee8fd6c5326e5a240/614336850/614336850.jpg@s_2,w_500,h_500","album_800_800":"","album_1000_1000":"http://qukufile2.qianqian.com/data2/pic/58754f79bde49e3ee8fd6c5326e5a240/614336850/614336850.jpg@s_2,w_1000,h_1000"},{"artist_id":"1483","language":"国语","pic_big":"http://qukufile2.qianqian.com/data2/pic/6a6489bd9627769702ac6c9a056b7446/670304618/670304618.jpg@s_2,w_150,h_150","pic_small":"http://qukufile2.qianqian.com/data2/pic/6a6489bd9627769702ac6c9a056b7446/670304618/670304618.jpg@s_2,w_90,h_90","country":"内地","area":"0","publishtime":"2016-06-27","album_no":"2","lrclink":"http://qukufile2.qianqian.com/data2/lrc/5d3f220582ba210d4059e60668ff7df8/670304620/670304620.lrc","copy_type":"1","hot":"96673","all_artist_ting_uid":"1557","resource_type":"0","is_new":"0","rank_change":"-1","rank":"11","all_artist_id":"1483","style":"流行","del_status":"0","relate_status":"0","toneid":"0","all_rate":"96,128,224,320,flac","file_duration":249,"has_mv_mobile":0,"versions":"","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","biaoshi":"lossless,perm-1","info":"","has_filmtv":"0","si_proxycompany":"华宇世博音乐文化（北京）有限公司-海蝶音乐","res_encryption_flag":"0","song_id":"264972770","title":"雅俗共赏","ting_uid":"1557","author":"许嵩","album_id":"264972901","album_title":"青年晚报","is_first_publish":0,"havehigh":2,"charge":0,"has_mv":0,"learn":0,"song_source":"web","piao_id":"0","korean_bb_song":"0","resource_type_ext":"0","mv_provider":"0000000000","artist_name":"许嵩","pic_radio":"http://qukufile2.qianqian.com/data2/pic/6a6489bd9627769702ac6c9a056b7446/670304618/670304618.jpg@s_2,w_300,h_300","pic_s500":"http://qukufile2.qianqian.com/data2/pic/6a6489bd9627769702ac6c9a056b7446/670304618/670304618.jpg@s_2,w_500,h_500","pic_premium":"http://qukufile2.qianqian.com/data2/pic/6a6489bd9627769702ac6c9a056b7446/670304618/670304618.jpg@s_2,w_500,h_500","pic_huge":"http://qukufile2.qianqian.com/data2/pic/6a6489bd9627769702ac6c9a056b7446/670304618/670304618.jpg@s_2,w_1000,h_1000","album_500_500":"http://qukufile2.qianqian.com/data2/pic/6a6489bd9627769702ac6c9a056b7446/670304618/670304618.jpg@s_2,w_500,h_500","album_800_800":"","album_1000_1000":"http://qukufile2.qianqian.com/data2/pic/6a6489bd9627769702ac6c9a056b7446/670304618/670304618.jpg@s_2,w_1000,h_1000"},{"artist_id":"371","language":"国语","pic_big":"http://qukufile2.qianqian.com/data2/pic/d3856609aa6068f9ae90002cc9cd321e/660133620/660133620.jpg@s_2,w_150,h_150","pic_small":"http://qukufile2.qianqian.com/data2/pic/d3856609aa6068f9ae90002cc9cd321e/660133620/660133620.jpg@s_2,w_90,h_90","country":"内地","area":"0","publishtime":"2018-08-24","album_no":"1","lrclink":"http://qukufile2.qianqian.com/data2/lrc/61f018d4766eb95ede8786dddc88ec9c/660138274/660138274.lrc","copy_type":"1","hot":"67172","all_artist_ting_uid":"1226","resource_type":"0","is_new":"0","rank_change":"0","rank":"12","all_artist_id":"371","style":"","del_status":"0","relate_status":"0","toneid":"0","all_rate":"96,128,224,320,flac","file_duration":212,"has_mv_mobile":0,"versions":"","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","biaoshi":"lossless,perm-1","info":"","has_filmtv":"0","si_proxycompany":"北京天乐浩世科技文化有限公司","res_encryption_flag":"0","song_id":"602870189","title":"我的爱（慕思《觉/醒》视频主题曲）","ting_uid":"1226","author":"许巍","album_id":"602870186","album_title":"我的爱（慕思《觉/醒》视频主题曲）","is_first_publish":0,"havehigh":2,"charge":0,"has_mv":0,"learn":0,"song_source":"web","piao_id":"0","korean_bb_song":"0","resource_type_ext":"0","mv_provider":"0000000000","artist_name":"许巍","pic_radio":"http://qukufile2.qianqian.com/data2/pic/d3856609aa6068f9ae90002cc9cd321e/660133620/660133620.jpg@s_2,w_300,h_300","pic_s500":"http://qukufile2.qianqian.com/data2/pic/d3856609aa6068f9ae90002cc9cd321e/660133620/660133620.jpg@s_2,w_500,h_500","pic_premium":"http://qukufile2.qianqian.com/data2/pic/d3856609aa6068f9ae90002cc9cd321e/660133620/660133620.jpg@s_2,w_500,h_500","pic_huge":"http://qukufile2.qianqian.com/data2/pic/d3856609aa6068f9ae90002cc9cd321e/660133620/660133620.jpg@s_2,w_1000,h_1000","album_500_500":"http://qukufile2.qianqian.com/data2/pic/d3856609aa6068f9ae90002cc9cd321e/660133620/660133620.jpg@s_2,w_500,h_500","album_800_800":"","album_1000_1000":"http://qukufile2.qianqian.com/data2/pic/d3856609aa6068f9ae90002cc9cd321e/660133620/660133620.jpg@s_2,w_1000,h_1000"},{"artist_id":"672743698","language":"国语","pic_big":"http://qukufile2.qianqian.com/data2/pic/4565ea8f42e10999d21660bece9e4b2a/672743726/672743726.jpg@s_2,w_150,h_150","pic_small":"http://qukufile2.qianqian.com/data2/pic/4565ea8f42e10999d21660bece9e4b2a/672743726/672743726.jpg@s_2,w_90,h_90","country":"内地","area":"0","publishtime":"2019-12-10","album_no":"1","lrclink":"http://qukufile2.qianqian.com/data2/lrc/afba7e0dfea004e572f055185c375d88/672743750/672743750.txt","copy_type":"1","hot":"49602","all_artist_ting_uid":"340614443","resource_type":"0","is_new":"1","rank_change":"0","rank":"13","all_artist_id":"672743698","style":"","del_status":"0","relate_status":"0","toneid":"0","all_rate":"96,128,224,320,flac","file_duration":174,"has_mv_mobile":0,"versions":"","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","biaoshi":"lossless,perm-1","info":"","has_filmtv":"0","si_proxycompany":"北京万上文化传媒有限公司","res_encryption_flag":"0","song_id":"672743701","title":"下山","ting_uid":"340614443","author":"千一","album_id":"672743699","album_title":"下山","is_first_publish":0,"havehigh":2,"charge":0,"has_mv":0,"learn":0,"song_source":"web","piao_id":"0","korean_bb_song":"0","resource_type_ext":"0","mv_provider":"0000000000","artist_name":"千一","pic_radio":"http://qukufile2.qianqian.com/data2/pic/4565ea8f42e10999d21660bece9e4b2a/672743726/672743726.jpg@s_2,w_300,h_300","pic_s500":"http://qukufile2.qianqian.com/data2/pic/4565ea8f42e10999d21660bece9e4b2a/672743726/672743726.jpg@s_2,w_500,h_500","pic_premium":"http://qukufile2.qianqian.com/data2/pic/4565ea8f42e10999d21660bece9e4b2a/672743726/672743726.jpg@s_2,w_500,h_500","pic_huge":"http://qukufile2.qianqian.com/data2/pic/4565ea8f42e10999d21660bece9e4b2a/672743726/672743726.jpg","album_500_500":"http://qukufile2.qianqian.com/data2/pic/4565ea8f42e10999d21660bece9e4b2a/672743726/672743726.jpg@s_2,w_500,h_500","album_800_800":"","album_1000_1000":"http://qukufile2.qianqian.com/data2/pic/4565ea8f42e10999d21660bece9e4b2a/672743726/672743726.jpg"},{"artist_id":"762","language":"国语","pic_big":"http://qukufile2.qianqian.com/data2/pic/015c6c99e1ced5261f624ef20cd7912f/660123395/660123395.jpg@s_2,w_150,h_150","pic_small":"http://qukufile2.qianqian.com/data2/pic/015c6c99e1ced5261f624ef20cd7912f/660123395/660123395.jpg@s_2,w_90,h_90","country":"内地","area":"0","publishtime":"2018-08-27","album_no":"1","lrclink":"http://qukufile2.qianqian.com/data2/lrc/53e4b8661c0908f841eb92900ee1f694/660124026/660124026.lrc","copy_type":"1","hot":"57810","all_artist_ting_uid":"1376,1854719","resource_type":"0","is_new":"0","rank_change":"0","rank":"14","all_artist_id":"762,6586","style":"","del_status":"0","relate_status":"0","toneid":"0","all_rate":"96,128,224,320,flac","file_duration":291,"has_mv_mobile":0,"versions":"","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","biaoshi":"lossless,perm-1","info":"","has_filmtv":"0","si_proxycompany":"北京普天同乐文化传媒有限公司","res_encryption_flag":"0","song_id":"602980311","title":"至少还有你爱我","ting_uid":"1376","author":"龙梅子,王娜","album_id":"602980305","album_title":"至少还有你爱我","is_first_publish":0,"havehigh":2,"charge":0,"has_mv":0,"learn":0,"song_source":"web","piao_id":"0","korean_bb_song":"0","resource_type_ext":"0","mv_provider":"0000000000","artist_name":"龙梅子,王娜","pic_radio":"http://qukufile2.qianqian.com/data2/pic/015c6c99e1ced5261f624ef20cd7912f/660123395/660123395.jpg@s_2,w_300,h_300","pic_s500":"http://qukufile2.qianqian.com/data2/pic/015c6c99e1ced5261f624ef20cd7912f/660123395/660123395.jpg@s_2,w_500,h_500","pic_premium":"http://qukufile2.qianqian.com/data2/pic/015c6c99e1ced5261f624ef20cd7912f/660123395/660123395.jpg@s_2,w_500,h_500","pic_huge":"http://qukufile2.qianqian.com/data2/pic/015c6c99e1ced5261f624ef20cd7912f/660123395/660123395.jpg","album_500_500":"http://qukufile2.qianqian.com/data2/pic/015c6c99e1ced5261f624ef20cd7912f/660123395/660123395.jpg@s_2,w_500,h_500","album_800_800":"","album_1000_1000":"http://qukufile2.qianqian.com/data2/pic/015c6c99e1ced5261f624ef20cd7912f/660123395/660123395.jpg"},{"artist_id":"606149056","language":"国语","pic_big":"http://qukufile2.qianqian.com/data2/pic/c9aa6f85bf036735c355a05dd373ff0b/606149058/606149058.png@s_2,w_150,h_150","pic_small":"http://qukufile2.qianqian.com/data2/pic/c9aa6f85bf036735c355a05dd373ff0b/606149058/606149058.png@s_2,w_90,h_90","country":"内地","area":"0","publishtime":"2017-06-19","album_no":"1","lrclink":"http://qukufile2.qianqian.com/data2/lrc/85d4f0173122c9fc5e901d4d5ef06bd2/608606729/608606729.lrc","copy_type":"1","hot":"60347","all_artist_ting_uid":"340462058","resource_type":"0","is_new":"0","rank_change":"0","rank":"15","all_artist_id":"606149056","style":"","del_status":"0","relate_status":"0","toneid":"0","all_rate":"96,128,224,320,flac","file_duration":338,"has_mv_mobile":0,"versions":"","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","biaoshi":"lossless,perm-1","info":"","has_filmtv":"0","si_proxycompany":"寿光坚诚文化传播有限公司","res_encryption_flag":"0","song_id":"606149060","title":"沙漠骆驼","ting_uid":"340462058","author":"展展与罗罗","album_id":"606149057","album_title":"沙漠骆驼","is_first_publish":0,"havehigh":2,"charge":0,"has_mv":0,"learn":0,"song_source":"web","piao_id":"0","korean_bb_song":"0","resource_type_ext":"0","mv_provider":"0000000000","artist_name":"展展与罗罗","pic_radio":"http://qukufile2.qianqian.com/data2/pic/c9aa6f85bf036735c355a05dd373ff0b/606149058/606149058.png@s_2,w_300,h_300","pic_s500":"http://qukufile2.qianqian.com/data2/pic/c9aa6f85bf036735c355a05dd373ff0b/606149058/606149058.png@s_2,w_500,h_500","pic_premium":"http://qukufile2.qianqian.com/data2/pic/c9aa6f85bf036735c355a05dd373ff0b/606149058/606149058.png@s_2,w_500,h_500","pic_huge":"http://qukufile2.qianqian.com/data2/pic/c9aa6f85bf036735c355a05dd373ff0b/606149058/606149058.png@s_2,w_1000,h_1000","album_500_500":"http://qukufile2.qianqian.com/data2/pic/c9aa6f85bf036735c355a05dd373ff0b/606149058/606149058.png@s_2,w_500,h_500","album_800_800":"","album_1000_1000":"http://qukufile2.qianqian.com/data2/pic/c9aa6f85bf036735c355a05dd373ff0b/606149058/606149058.png@s_2,w_1000,h_1000"},{"artist_id":"88","language":"国语","pic_big":"http://qukufile2.qianqian.com/data2/pic/49e6161afb13e3eda9d1cb4e304561a2/676820947/676820947.jpg@s_2,w_150,h_150","pic_small":"http://qukufile2.qianqian.com/data2/pic/49e6161afb13e3eda9d1cb4e304561a2/676820947/676820947.jpg@s_2,w_90,h_90","country":"内地","area":"0","publishtime":"2016-07-18","album_no":"3","lrclink":"http://qukufile2.qianqian.com/data2/lrc/f63a5782603c444ad7c66b2d67bd534f/667891911/667891911.lrc","copy_type":"1","hot":"17434","all_artist_ting_uid":"2517","resource_type":"0","is_new":"0","rank_change":"0","rank":"16","all_artist_id":"88","style":"影视原声","del_status":"0","relate_status":"0","toneid":"0","all_rate":"96,128,224,320,flac","file_duration":279,"has_mv_mobile":0,"versions":"","bitrate_fee":"{\"0\":\"129|-1\",\"1\":\"-1|-1\"}","biaoshi":"lossless,vip,perm-1","info":"电影《精灵王座》主题曲","has_filmtv":"0","si_proxycompany":"华宇世博音乐文化（北京）有限公司-普通代理","res_encryption_flag":"0","song_id":"266942077","title":"我好像在哪见过你","ting_uid":"2517","author":"薛之谦","album_id":"241838068","album_title":"初学者","is_first_publish":0,"havehigh":2,"charge":0,"has_mv":0,"learn":0,"song_source":"web","piao_id":"0","korean_bb_song":"0","resource_type_ext":"2","mv_provider":"0000000000","artist_name":"薛之谦","pic_radio":"http://qukufile2.qianqian.com/data2/pic/49e6161afb13e3eda9d1cb4e304561a2/676820947/676820947.jpg@s_2,w_300,h_300","pic_s500":"http://qukufile2.qianqian.com/data2/pic/49e6161afb13e3eda9d1cb4e304561a2/676820947/676820947.jpg@s_2,w_500,h_500","pic_premium":"http://qukufile2.qianqian.com/data2/pic/49e6161afb13e3eda9d1cb4e304561a2/676820947/676820947.jpg@s_2,w_500,h_500","pic_huge":"http://qukufile2.qianqian.com/data2/pic/49e6161afb13e3eda9d1cb4e304561a2/676820947/676820947.jpg@s_2,w_1000,h_1000","album_500_500":"http://qukufile2.qianqian.com/data2/pic/49e6161afb13e3eda9d1cb4e304561a2/676820947/676820947.jpg@s_2,w_500,h_500","album_800_800":"","album_1000_1000":"http://qukufile2.qianqian.com/data2/pic/49e6161afb13e3eda9d1cb4e304561a2/676820947/676820947.jpg@s_2,w_1000,h_1000"},{"artist_id":"762","language":"国语","pic_big":"http://qukufile2.qianqian.com/data2/pic/e3feb8c1acbc7680dab69f244413bc49/660136668/660136668.jpg@s_2,w_150,h_150","pic_small":"http://qukufile2.qianqian.com/data2/pic/e3feb8c1acbc7680dab69f244413bc49/660136668/660136668.jpg@s_2,w_90,h_90","country":"内地","area":"0","publishtime":"2018-08-06","album_no":"1","lrclink":"http://qukufile2.qianqian.com/data2/lrc/a881f55cbc12249ede9592fe207d6a9d/660140072/660140072.lrc","copy_type":"1","hot":"49811","all_artist_ting_uid":"1376,1799","resource_type":"0","is_new":"0","rank_change":"0","rank":"17","all_artist_id":"762,3238","style":"","del_status":"0","relate_status":"0","toneid":"0","all_rate":"96,128,224,320,flac","file_duration":215,"has_mv_mobile":0,"versions":"","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","biaoshi":"lossless,perm-1","info":"","has_filmtv":"0","si_proxycompany":"北京普天同乐文化传媒有限公司","res_encryption_flag":"0","song_id":"601914158","title":"都说","ting_uid":"1376","author":"龙梅子,老猫","album_id":"601914154","album_title":"都说","is_first_publish":0,"havehigh":2,"charge":0,"has_mv":0,"learn":0,"song_source":"web","piao_id":"0","korean_bb_song":"0","resource_type_ext":"0","mv_provider":"0000000000","artist_name":"龙梅子,老猫","pic_radio":"http://qukufile2.qianqian.com/data2/pic/e3feb8c1acbc7680dab69f244413bc49/660136668/660136668.jpg@s_2,w_300,h_300","pic_s500":"http://qukufile2.qianqian.com/data2/pic/e3feb8c1acbc7680dab69f244413bc49/660136668/660136668.jpg@s_2,w_500,h_500","pic_premium":"http://qukufile2.qianqian.com/data2/pic/e3feb8c1acbc7680dab69f244413bc49/660136668/660136668.jpg@s_2,w_500,h_500","pic_huge":"http://qukufile2.qianqian.com/data2/pic/e3feb8c1acbc7680dab69f244413bc49/660136668/660136668.jpg","album_500_500":"http://qukufile2.qianqian.com/data2/pic/e3feb8c1acbc7680dab69f244413bc49/660136668/660136668.jpg@s_2,w_500,h_500","album_800_800":"","album_1000_1000":"http://qukufile2.qianqian.com/data2/pic/e3feb8c1acbc7680dab69f244413bc49/660136668/660136668.jpg"},{"artist_id":"15","language":"国语","pic_big":"http://qukufile2.qianqian.com/data2/pic/4865939a77b87edc79789df87b6f22d8/569080825/569080825.png@s_2,w_150,h_150","pic_small":"http://qukufile2.qianqian.com/data2/pic/4865939a77b87edc79789df87b6f22d8/569080825/569080825.png@s_2,w_90,h_90","country":"内地","area":"0","publishtime":"2018-01-11","album_no":"1","lrclink":"http://qukufile2.qianqian.com/data2/lrc/1a498130be0a54f75e44ee385103d6d5/608604843/608604843.lrc","copy_type":"3","hot":"55257","all_artist_ting_uid":"45561","resource_type":"0","is_new":"0","rank_change":"0","rank":"18","all_artist_id":"15","style":"","del_status":"0","relate_status":"0","toneid":"0","all_rate":"96,128,224,320,flac","file_duration":289,"has_mv_mobile":0,"versions":"影视原声","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","biaoshi":"lossless,perm-1","info":"","has_filmtv":"0","si_proxycompany":"上海腾讯企鹅影视文化传播有限公司","res_encryption_flag":"0","song_id":"569080829","title":"无问西东","ting_uid":"45561","author":"王菲","album_id":"569080827","album_title":"无问西东","is_first_publish":0,"havehigh":2,"charge":0,"has_mv":0,"learn":0,"song_source":"web","piao_id":"0","korean_bb_song":"0","resource_type_ext":"0","mv_provider":"0000000000","artist_name":"王菲","pic_radio":"http://qukufile2.qianqian.com/data2/pic/4865939a77b87edc79789df87b6f22d8/569080825/569080825.png@s_2,w_300,h_300","pic_s500":"http://qukufile2.qianqian.com/data2/pic/4865939a77b87edc79789df87b6f22d8/569080825/569080825.png@s_2,w_500,h_500","pic_premium":"http://qukufile2.qianqian.com/data2/pic/4865939a77b87edc79789df87b6f22d8/569080825/569080825.png@s_2,w_500,h_500","pic_huge":"http://qukufile2.qianqian.com/data2/pic/4865939a77b87edc79789df87b6f22d8/569080825/569080825.png@s_2,w_1000,h_1000","album_500_500":"http://qukufile2.qianqian.com/data2/pic/4865939a77b87edc79789df87b6f22d8/569080825/569080825.png@s_2,w_500,h_500","album_800_800":"","album_1000_1000":"http://qukufile2.qianqian.com/data2/pic/4865939a77b87edc79789df87b6f22d8/569080825/569080825.png@s_2,w_1000,h_1000"},{"artist_id":"88","language":"国语","pic_big":"http://qukufile2.qianqian.com/data2/pic/49e6161afb13e3eda9d1cb4e304561a2/676820947/676820947.jpg@s_2,w_150,h_150","pic_small":"http://qukufile2.qianqian.com/data2/pic/49e6161afb13e3eda9d1cb4e304561a2/676820947/676820947.jpg@s_2,w_90,h_90","country":"内地","area":"0","publishtime":"2016-07-18","album_no":"5","lrclink":"http://qukufile2.qianqian.com/data2/lrc/cef5a4438ed6f55e4d2d8d0ddb5d27d6/667891913/667891913.lrc","copy_type":"1","hot":"16016","all_artist_ting_uid":"2517","resource_type":"0","is_new":"0","rank_change":"0","rank":"19","all_artist_id":"88","style":"流行","del_status":"0","relate_status":"0","toneid":"0","all_rate":"96,128,224,320,flac","file_duration":290,"has_mv_mobile":0,"versions":"","bitrate_fee":"{\"0\":\"129|-1\",\"1\":\"-1|-1\"}","biaoshi":"lossless,vip,perm-1","info":"","has_filmtv":"0","si_proxycompany":"华宇世博音乐文化（北京）有限公司-普通代理","res_encryption_flag":"0","song_id":"241838066","title":"绅士","ting_uid":"2517","author":"薛之谦","album_id":"241838068","album_title":"初学者","is_first_publish":0,"havehigh":2,"charge":0,"has_mv":0,"learn":0,"song_source":"web","piao_id":"0","korean_bb_song":"0","resource_type_ext":"2","mv_provider":"0000000000","artist_name":"薛之谦","pic_radio":"http://qukufile2.qianqian.com/data2/pic/49e6161afb13e3eda9d1cb4e304561a2/676820947/676820947.jpg@s_2,w_300,h_300","pic_s500":"http://qukufile2.qianqian.com/data2/pic/49e6161afb13e3eda9d1cb4e304561a2/676820947/676820947.jpg@s_2,w_500,h_500","pic_premium":"http://qukufile2.qianqian.com/data2/pic/49e6161afb13e3eda9d1cb4e304561a2/676820947/676820947.jpg@s_2,w_500,h_500","pic_huge":"http://qukufile2.qianqian.com/data2/pic/49e6161afb13e3eda9d1cb4e304561a2/676820947/676820947.jpg@s_2,w_1000,h_1000","album_500_500":"http://qukufile2.qianqian.com/data2/pic/49e6161afb13e3eda9d1cb4e304561a2/676820947/676820947.jpg@s_2,w_500,h_500","album_800_800":"","album_1000_1000":"http://qukufile2.qianqian.com/data2/pic/49e6161afb13e3eda9d1cb4e304561a2/676820947/676820947.jpg@s_2,w_1000,h_1000"},{"artist_id":"5418132","language":"国语","pic_big":"http://qukufile2.qianqian.com/data2/pic/53da8303e9557437e42ce22848395010/274360561/274360561.jpg@s_2,w_150,h_150","pic_small":"http://qukufile2.qianqian.com/data2/pic/53da8303e9557437e42ce22848395010/274360561/274360561.jpg@s_2,w_90,h_90","country":"内地","area":"0","publishtime":"2016-10-19","album_no":"3","lrclink":"http://qukufile2.qianqian.com/data2/lrc/ca3e3c0bd56314440869399e65e2631f/274452059/274452059.lrc","copy_type":"1","hot":"60004","all_artist_ting_uid":"415077","resource_type":"0","is_new":"0","rank_change":"0","rank":"20","all_artist_id":"5418132","style":"流行","del_status":"0","relate_status":"0","toneid":"0","all_rate":"96,128,224,320","file_duration":157,"has_mv_mobile":0,"versions":"","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","biaoshi":"perm-1","info":"","has_filmtv":"0","si_proxycompany":"阳谷一阳文化传媒有限公司","res_encryption_flag":"0","song_id":"274360560","title":"九儿","ting_uid":"415077","author":"李雨儿","album_id":"274360554","album_title":"红色恋人","is_first_publish":0,"havehigh":2,"charge":0,"has_mv":0,"learn":0,"song_source":"web","piao_id":"0","korean_bb_song":"0","resource_type_ext":"0","mv_provider":"0000000000","artist_name":"李雨儿","pic_radio":"http://qukufile2.qianqian.com/data2/pic/53da8303e9557437e42ce22848395010/274360561/274360561.jpg@s_2,w_300,h_300","pic_s500":"http://qukufile2.qianqian.com/data2/pic/53da8303e9557437e42ce22848395010/274360561/274360561.jpg@s_2,w_500,h_500","pic_premium":"http://qukufile2.qianqian.com/data2/pic/53da8303e9557437e42ce22848395010/274360561/274360561.jpg@s_2,w_500,h_500","pic_huge":"http://qukufile2.qianqian.com/data2/pic/53da8303e9557437e42ce22848395010/274360561/274360561.jpg@s_2,w_1000,h_1000","album_500_500":"http://qukufile2.qianqian.com/data2/pic/53da8303e9557437e42ce22848395010/274360561/274360561.jpg@s_2,w_500,h_500","album_800_800":"","album_1000_1000":"http://qukufile2.qianqian.com/data2/pic/53da8303e9557437e42ce22848395010/274360561/274360561.jpg@s_2,w_1000,h_1000"}]
     * billboard : {"billboard_type":"2","billboard_no":"3118","update_date":"2020-04-22","billboard_songnum":"1365","havemore":1,"name":"热歌榜","comment":"该榜单是根据千千音乐平台歌曲每周播放量自动生成的数据榜单，统计范围为千千音乐平台上的全部歌曲，每日更新一次","pic_s192":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_1452f36a8dc430ccdb8f6e57be6df2ee.jpg","pic_s640":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_361aa8612dd9dd8474daf77040f7079d.jpg","pic_s444":"http://hiphotos.qianqian.com/ting/pic/item/c83d70cf3bc79f3d98ca8e36b8a1cd11728b2988.jpg","pic_s260":"http://hiphotos.qianqian.com/ting/pic/item/838ba61ea8d3fd1f1326c83c324e251f95ca5f8c.jpg","pic_s210":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_734232335ef76f5a05179797875817f3.jpg","web_url":"http://music.baidu.com/top/dayhot","color":"0xDC5900","bg_color":"0xFBEFE6","bg_pic":"http://business0.qianqian.com/qianqian/file/5bfe5026de816_689.png"}
     */

    private BillBoardBean.Billboard billboard;
    private List<BillBoardBean.Song> song_list;

    public BillBoardBean.Billboard getBillboard() {
        return billboard;
    }

    public void setBillboard(BillBoardBean.Billboard billboard) {
        this.billboard = billboard;
    }

    public List<BillBoardBean.Song> getSong_list() {
        return song_list;
    }

    public void setSong_list(List<BillBoardBean.Song> song_list) {
        this.song_list = song_list;
    }

    public static class Billboard {
        /**
         * billboard_type : 2
         * billboard_no : 3118
         * update_date : 2020-04-22
         * billboard_songnum : 1365
         * havemore : 1
         * name : 热歌榜
         * comment : 该榜单是根据千千音乐平台歌曲每周播放量自动生成的数据榜单，统计范围为千千音乐平台上的全部歌曲，每日更新一次
         * pic_s192 : http://business.cdn.qianqian.com/qianqian/pic/bos_client_1452f36a8dc430ccdb8f6e57be6df2ee.jpg
         * pic_s640 : http://business.cdn.qianqian.com/qianqian/pic/bos_client_361aa8612dd9dd8474daf77040f7079d.jpg
         * pic_s444 : http://hiphotos.qianqian.com/ting/pic/item/c83d70cf3bc79f3d98ca8e36b8a1cd11728b2988.jpg
         * pic_s260 : http://hiphotos.qianqian.com/ting/pic/item/838ba61ea8d3fd1f1326c83c324e251f95ca5f8c.jpg
         * pic_s210 : http://business.cdn.qianqian.com/qianqian/pic/bos_client_734232335ef76f5a05179797875817f3.jpg
         * web_url : http://music.baidu.com/top/dayhot
         * color : 0xDC5900
         * bg_color : 0xFBEFE6
         * bg_pic : http://business0.qianqian.com/qianqian/file/5bfe5026de816_689.png
         */

        private String billboard_type;
        private String billboard_no;
        private String update_date;
        private String billboard_songnum;
        private int havemore;
        private String name;
        private String comment;
        private String pic_s192;
        private String pic_s640;
        private String pic_s444;
        private String pic_s260;
        private String pic_s210;
        private String web_url;
        private String color;
        private String bg_color;
        private String bg_pic;

        public String getBillboard_type() {
            return billboard_type;
        }

        public void setBillboard_type(String billboard_type) {
            this.billboard_type = billboard_type;
        }

        public String getBillboard_no() {
            return billboard_no;
        }

        public void setBillboard_no(String billboard_no) {
            this.billboard_no = billboard_no;
        }

        public String getUpdate_date() {
            return update_date;
        }

        public void setUpdate_date(String update_date) {
            this.update_date = update_date;
        }

        public String getBillboard_songnum() {
            return billboard_songnum;
        }

        public void setBillboard_songnum(String billboard_songnum) {
            this.billboard_songnum = billboard_songnum;
        }

        public int getHavemore() {
            return havemore;
        }

        public void setHavemore(int havemore) {
            this.havemore = havemore;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getPic_s192() {
            return pic_s192;
        }

        public void setPic_s192(String pic_s192) {
            this.pic_s192 = pic_s192;
        }

        public String getPic_s640() {
            return pic_s640;
        }

        public void setPic_s640(String pic_s640) {
            this.pic_s640 = pic_s640;
        }

        public String getPic_s444() {
            return pic_s444;
        }

        public void setPic_s444(String pic_s444) {
            this.pic_s444 = pic_s444;
        }

        public String getPic_s260() {
            return pic_s260;
        }

        public void setPic_s260(String pic_s260) {
            this.pic_s260 = pic_s260;
        }

        public String getPic_s210() {
            return pic_s210;
        }

        public void setPic_s210(String pic_s210) {
            this.pic_s210 = pic_s210;
        }

        public String getWeb_url() {
            return web_url;
        }

        public void setWeb_url(String web_url) {
            this.web_url = web_url;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getBg_color() {
            return bg_color;
        }

        public void setBg_color(String bg_color) {
            this.bg_color = bg_color;
        }

        public String getBg_pic() {
            return bg_pic;
        }

        public void setBg_pic(String bg_pic) {
            this.bg_pic = bg_pic;
        }
    }

    public static class Song {
        /**
         * artist_id : 672865435
         * language : 国语
         * pic_big : http://qukufile2.qianqian.com/data2/pic/b733a1a9fc0f63c7015be29b7b840b66/672866107/672866107.jpg@s_2,w_150,h_150
         * pic_small : http://qukufile2.qianqian.com/data2/pic/b733a1a9fc0f63c7015be29b7b840b66/672866107/672866107.jpg@s_2,w_90,h_90
         * country : 内地
         * area : 0
         * publishtime : 2019-12-16
         * album_no : 1
         * lrclink : http://qukufile2.qianqian.com/data2/lrc/4589294157b56806f6ff597660c84329/672866365/672866365.txt
         * copy_type : 1
         * hot : 185951
         * all_artist_ting_uid : 340615848
         * resource_type : 0
         * is_new : 1
         * rank_change : 0
         * rank : 1
         * all_artist_id : 672865435
         * style :
         * del_status : 0
         * relate_status : 0
         * toneid : 0
         * all_rate : 96,128,224,320,flac
         * file_duration : 175
         * has_mv_mobile : 0
         * versions :
         * bitrate_fee : {"0":"0|0","1":"0|0"}
         * biaoshi : lossless,perm-1
         * info :
         * has_filmtv : 0
         * si_proxycompany : 北京万上文化传媒有限公司
         * res_encryption_flag : 0
         * song_id : 672865438
         * title : 桥边姑娘
         * ting_uid : 340615848
         * author : 舞蹈女神诺涵
         * album_id : 672865436
         * album_title : 桥边姑娘
         * is_first_publish : 0
         * havehigh : 2
         * charge : 0
         * has_mv : 0
         * learn : 0
         * song_source : web
         * piao_id : 0
         * korean_bb_song : 0
         * resource_type_ext : 0
         * mv_provider : 0000000000
         * artist_name : 舞蹈女神诺涵
         * pic_radio : http://qukufile2.qianqian.com/data2/pic/b733a1a9fc0f63c7015be29b7b840b66/672866107/672866107.jpg@s_2,w_300,h_300
         * pic_s500 : http://qukufile2.qianqian.com/data2/pic/b733a1a9fc0f63c7015be29b7b840b66/672866107/672866107.jpg@s_2,w_500,h_500
         * pic_premium : http://qukufile2.qianqian.com/data2/pic/b733a1a9fc0f63c7015be29b7b840b66/672866107/672866107.jpg@s_2,w_500,h_500
         * pic_huge : http://qukufile2.qianqian.com/data2/pic/b733a1a9fc0f63c7015be29b7b840b66/672866107/672866107.jpg
         * album_500_500 : http://qukufile2.qianqian.com/data2/pic/b733a1a9fc0f63c7015be29b7b840b66/672866107/672866107.jpg@s_2,w_500,h_500
         * album_800_800 :
         * album_1000_1000 : http://qukufile2.qianqian.com/data2/pic/b733a1a9fc0f63c7015be29b7b840b66/672866107/672866107.jpg
         */

        private String artist_id;
        private String language;
        private String pic_big;
        private String pic_small;
        private String country;
        private String area;
        private String publishtime;
        private String album_no;
        private String lrclink;
        private String copy_type;
        private String hot;
        private String all_artist_ting_uid;
        private String resource_type;
        private String is_new;
        private String rank_change;
        private String rank;
        private String all_artist_id;
        private String style;
        private String del_status;
        private String relate_status;
        private String toneid;
        private String all_rate;
        private int file_duration;
        private int has_mv_mobile;
        private String versions;
        private String bitrate_fee;
        private String biaoshi;
        private String info;
        private String has_filmtv;
        private String si_proxycompany;
        private String res_encryption_flag;
        private String song_id;
        private String title;
        private String ting_uid;
        private String author;
        private String album_id;
        private String album_title;
        private int is_first_publish;
        private int havehigh;
        private int charge;
        private int has_mv;
        private int learn;
        private String song_source;
        private String piao_id;
        private String korean_bb_song;
        private String resource_type_ext;
        private String mv_provider;
        private String artist_name;
        private String pic_radio;
        private String pic_s500;
        private String pic_premium;
        private String pic_huge;
        private String album_500_500;
        private String album_800_800;
        private String album_1000_1000;

        public String getArtist_id() {
            return artist_id;
        }

        public void setArtist_id(String artist_id) {
            this.artist_id = artist_id;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getPic_big() {
            return pic_big;
        }

        public void setPic_big(String pic_big) {
            this.pic_big = pic_big;
        }

        public String getPic_small() {
            return pic_small;
        }

        public void setPic_small(String pic_small) {
            this.pic_small = pic_small;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getPublishtime() {
            return publishtime;
        }

        public void setPublishtime(String publishtime) {
            this.publishtime = publishtime;
        }

        public String getAlbum_no() {
            return album_no;
        }

        public void setAlbum_no(String album_no) {
            this.album_no = album_no;
        }

        public String getLrclink() {
            return lrclink;
        }

        public void setLrclink(String lrclink) {
            this.lrclink = lrclink;
        }

        public String getCopy_type() {
            return copy_type;
        }

        public void setCopy_type(String copy_type) {
            this.copy_type = copy_type;
        }

        public String getHot() {
            return hot;
        }

        public void setHot(String hot) {
            this.hot = hot;
        }

        public String getAll_artist_ting_uid() {
            return all_artist_ting_uid;
        }

        public void setAll_artist_ting_uid(String all_artist_ting_uid) {
            this.all_artist_ting_uid = all_artist_ting_uid;
        }

        public String getResource_type() {
            return resource_type;
        }

        public void setResource_type(String resource_type) {
            this.resource_type = resource_type;
        }

        public String getIs_new() {
            return is_new;
        }

        public void setIs_new(String is_new) {
            this.is_new = is_new;
        }

        public String getRank_change() {
            return rank_change;
        }

        public void setRank_change(String rank_change) {
            this.rank_change = rank_change;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getAll_artist_id() {
            return all_artist_id;
        }

        public void setAll_artist_id(String all_artist_id) {
            this.all_artist_id = all_artist_id;
        }

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public String getDel_status() {
            return del_status;
        }

        public void setDel_status(String del_status) {
            this.del_status = del_status;
        }

        public String getRelate_status() {
            return relate_status;
        }

        public void setRelate_status(String relate_status) {
            this.relate_status = relate_status;
        }

        public String getToneid() {
            return toneid;
        }

        public void setToneid(String toneid) {
            this.toneid = toneid;
        }

        public String getAll_rate() {
            return all_rate;
        }

        public void setAll_rate(String all_rate) {
            this.all_rate = all_rate;
        }

        public int getFile_duration() {
            return file_duration;
        }

        public void setFile_duration(int file_duration) {
            this.file_duration = file_duration;
        }

        public int getHas_mv_mobile() {
            return has_mv_mobile;
        }

        public void setHas_mv_mobile(int has_mv_mobile) {
            this.has_mv_mobile = has_mv_mobile;
        }

        public String getVersions() {
            return versions;
        }

        public void setVersions(String versions) {
            this.versions = versions;
        }

        public String getBitrate_fee() {
            return bitrate_fee;
        }

        public void setBitrate_fee(String bitrate_fee) {
            this.bitrate_fee = bitrate_fee;
        }

        public String getBiaoshi() {
            return biaoshi;
        }

        public void setBiaoshi(String biaoshi) {
            this.biaoshi = biaoshi;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getHas_filmtv() {
            return has_filmtv;
        }

        public void setHas_filmtv(String has_filmtv) {
            this.has_filmtv = has_filmtv;
        }

        public String getSi_proxycompany() {
            return si_proxycompany;
        }

        public void setSi_proxycompany(String si_proxycompany) {
            this.si_proxycompany = si_proxycompany;
        }

        public String getRes_encryption_flag() {
            return res_encryption_flag;
        }

        public void setRes_encryption_flag(String res_encryption_flag) {
            this.res_encryption_flag = res_encryption_flag;
        }

        public String getSong_id() {
            return song_id;
        }

        public void setSong_id(String song_id) {
            this.song_id = song_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTing_uid() {
            return ting_uid;
        }

        public void setTing_uid(String ting_uid) {
            this.ting_uid = ting_uid;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getAlbum_id() {
            return album_id;
        }

        public void setAlbum_id(String album_id) {
            this.album_id = album_id;
        }

        public String getAlbum_title() {
            return album_title;
        }

        public void setAlbum_title(String album_title) {
            this.album_title = album_title;
        }

        public int getIs_first_publish() {
            return is_first_publish;
        }

        public void setIs_first_publish(int is_first_publish) {
            this.is_first_publish = is_first_publish;
        }

        public int getHavehigh() {
            return havehigh;
        }

        public void setHavehigh(int havehigh) {
            this.havehigh = havehigh;
        }

        public int getCharge() {
            return charge;
        }

        public void setCharge(int charge) {
            this.charge = charge;
        }

        public int getHas_mv() {
            return has_mv;
        }

        public void setHas_mv(int has_mv) {
            this.has_mv = has_mv;
        }

        public int getLearn() {
            return learn;
        }

        public void setLearn(int learn) {
            this.learn = learn;
        }

        public String getSong_source() {
            return song_source;
        }

        public void setSong_source(String song_source) {
            this.song_source = song_source;
        }

        public String getPiao_id() {
            return piao_id;
        }

        public void setPiao_id(String piao_id) {
            this.piao_id = piao_id;
        }

        public String getKorean_bb_song() {
            return korean_bb_song;
        }

        public void setKorean_bb_song(String korean_bb_song) {
            this.korean_bb_song = korean_bb_song;
        }

        public String getResource_type_ext() {
            return resource_type_ext;
        }

        public void setResource_type_ext(String resource_type_ext) {
            this.resource_type_ext = resource_type_ext;
        }

        public String getMv_provider() {
            return mv_provider;
        }

        public void setMv_provider(String mv_provider) {
            this.mv_provider = mv_provider;
        }

        public String getArtist_name() {
            return artist_name;
        }

        public void setArtist_name(String artist_name) {
            this.artist_name = artist_name;
        }

        public String getPic_radio() {
            return pic_radio;
        }

        public void setPic_radio(String pic_radio) {
            this.pic_radio = pic_radio;
        }

        public String getPic_s500() {
            return pic_s500;
        }

        public void setPic_s500(String pic_s500) {
            this.pic_s500 = pic_s500;
        }

        public String getPic_premium() {
            return pic_premium;
        }

        public void setPic_premium(String pic_premium) {
            this.pic_premium = pic_premium;
        }

        public String getPic_huge() {
            return pic_huge;
        }

        public void setPic_huge(String pic_huge) {
            this.pic_huge = pic_huge;
        }

        public String getAlbum_500_500() {
            return album_500_500;
        }

        public void setAlbum_500_500(String album_500_500) {
            this.album_500_500 = album_500_500;
        }

        public String getAlbum_800_800() {
            return album_800_800;
        }

        public void setAlbum_800_800(String album_800_800) {
            this.album_800_800 = album_800_800;
        }

        public String getAlbum_1000_1000() {
            return album_1000_1000;
        }

        public void setAlbum_1000_1000(String album_1000_1000) {
            this.album_1000_1000 = album_1000_1000;
        }
    }
}
