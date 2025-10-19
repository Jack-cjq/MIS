package org.example.service.Impl;

import org.example.model.CompetitionDictModel;
import org.example.repository.CompetitionDictRepository;
import org.example.service.CompetitionDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class CompetitionDictServiceImpl implements CompetitionDictService {

    @Autowired
    private CompetitionDictRepository competitionDictRepository;

    @PostConstruct
    public void initCompetitionData() {
        // 检查是否已有数据，避免重复初始化
        if (competitionDictRepository.count() == 0) {
            List<CompetitionDictModel> competitions = createAllCompetitions();
            competitionDictRepository.saveAll(competitions);
            System.out.println("成功初始化 " + competitions.size() + " 条竞赛数据");
        }
    }

    private List<CompetitionDictModel> createAllCompetitions() {
        List<CompetitionDictModel> competitions = new ArrayList<>();

        // 重大赛事（1-29）
        competitions.add(createCompetition(1, "\"挑战杯\"全国大学生课外学术科技作品竞赛", "重大赛事"));
        competitions.add(createCompetition(2, "\"挑战杯\"中国大学生创业计划竞赛", "重大赛事"));
        competitions.add(createCompetition(3, "中国\"互联网+\"大学生创新创业大赛", "重大赛事"));
        competitions.add(createCompetition(4, "中国研究生机器人创新设计大赛", "重大赛事"));
        competitions.add(createCompetition(5, "中国研究生电子设计竞赛", "重大赛事"));
        competitions.add(createCompetition(6, "中国研究生能源装备创新设计大赛", "重大赛事"));
        competitions.add(createCompetition(7, "中国\"华为杯\"大学生新材料创新设计大赛", "重大赛事"));
        competitions.add(createCompetition(8, "国际遗传工程机器大赛", "重大赛事"));
        competitions.add(createCompetition(9, "中国研究生智慧城市技术与创意设计大赛", "重大赛事"));
        competitions.add(createCompetition(10, "中国研究生工程管理案例大赛", "重大赛事"));
        competitions.add(createCompetition(11, "全国周培源大学生力学竞赛", "重大赛事"));
        competitions.add(createCompetition(12, "全国大学生物联网设计竞赛", "重大赛事"));
        competitions.add(createCompetition(13, "中国研究生创\"芯\"大赛", "重大赛事"));
        competitions.add(createCompetition(14, "中国研究生人工智能创新大赛", "重大赛事"));
        competitions.add(createCompetition(15, "中国研究生网络安全创新大赛", "重大赛事"));
        competitions.add(createCompetition(16, "中国研究生未来飞行器创新大赛", "重大赛事"));
        competitions.add(createCompetition(17, "全国大学生广告艺术设计大赛", "重大赛事"));
        competitions.add(createCompetition(18, "中国研究生\"美丽中国\"创新设计大赛", "重大赛事"));
        competitions.add(createCompetition(19, "全国大学生工业设计大赛", "重大赛事"));
        competitions.add(createCompetition(20, "全国大学生电子商务\"创新、创意及创业\"挑战赛", "重大赛事"));
        competitions.add(createCompetition(21, "中国研究生公共管理案例大赛", "重大赛事"));
        competitions.add(createCompetition(22, "中国研究生企业管理创新大赛", "重大赛事"));
        competitions.add(createCompetition(23, "中国研究生\"双碳\"创新与创业大赛", "重大赛事"));
        competitions.add(createCompetition(24, "中国研究生金融科技创新大赛", "重大赛事"));
        competitions.add(createCompetition(25, "全国大学生集成电路创新创业大赛", "重大赛事"));
        competitions.add(createCompetition(26, "中国研究生数学建模竞赛", "重大赛事"));
        competitions.add(createCompetition(27, "中国研究生操作系统开源创新大赛", "重大赛事"));
        competitions.add(createCompetition(28, "中国研究生\"文化中国\"两创大赛", "重大赛事"));
        competitions.add(createCompetition(29, "中国研究生国际中文教育案例大赛", "重大赛事"));

        // 重点赛事（30-87）
        competitions.add(createCompetition(30, "全国大学生机器人大赛-RoboMaster、RoboCom", "重点赛事"));
        competitions.add(createCompetition(31, "中国大学生机械工程创新创意大赛", "重点赛事"));
        competitions.add(createCompetition(32, "全国大学生节能减排社会实践与科技竞赛", "重点赛事"));
        competitions.add(createCompetition(33, "全国大学生智能汽车竞赛", "重点赛事"));
        competitions.add(createCompetition(34, "\"大唐杯\"全国大学生新一代信息通信技术大赛", "重点赛事"));
        competitions.add(createCompetition(35, "\"西门子杯\"中国智能制造挑战赛", "重点赛事"));
        competitions.add(createCompetition(36, "\"欧姆龙杯\"自动化控制应用设计大赛", "重点赛事"));
        competitions.add(createCompetition(37, "中国大学生高分子材料创新创业大赛", "重点赛事"));
        competitions.add(createCompetition(38, "全国大学生制药工程设计竞赛", "重点赛事"));
        competitions.add(createCompetition(39, "全国大学生岩土工程竞赛", "重点赛事"));
        competitions.add(createCompetition(40, "园冶杯大学生国际竞赛", "重点赛事"));
        competitions.add(createCompetition(41, "UIA霍普杯国际大学生建筑设计竞赛", "重点赛事"));
        competitions.add(createCompetition(42, "谷雨杯全国大学生可持续建筑设计大赛", "重点赛事"));
        competitions.add(createCompetition(43, "全国大学生混凝土设计大赛", "重点赛事"));
        competitions.add(createCompetition(44, "全国大学生加筋土挡墙设计大赛", "重点赛事"));
        competitions.add(createCompetition(45, "全国高校BIM毕业设计创新大赛", "重点赛事"));
        competitions.add(createCompetition(46, "水科学数值模拟创新大赛", "重点赛事"));
        competitions.add(createCompetition(47, "斯维尔杯全国高校BIM应用技能竞赛", "重点赛事"));
        competitions.add(createCompetition(48, "中日韩大学生风景园林设计竞赛", "重点赛事"));
        competitions.add(createCompetition(49, "大学生全国先进成图技术与产品信息建模创新大赛", "重点赛事"));
        competitions.add(createCompetition(50, "中国机器人及人工智能大赛", "重点赛事"));
        competitions.add(createCompetition(51, "\"中国软件杯\"大学生软件设计大赛", "重点赛事"));
        competitions.add(createCompetition(52, "全球校园人工智能算法精英大赛", "重点赛事"));
        competitions.add(createCompetition(53, "中国高校计算机大赛", "重点赛事"));
        competitions.add(createCompetition(54, "CUMMULUS GRBEN", "重点赛事"));
        competitions.add(createCompetition(55, "湖北高校美术与设计大展", "重点赛事"));
        competitions.add(createCompetition(56, "两岸新锐设计竞赛·华灿奖", "重点赛事"));
        competitions.add(createCompetition(57, "米兰设计周-中国高校设计学科师生优秀作品展", "重点赛事"));
        competitions.add(createCompetition(58, "中国人居环境设计学年奖", "重点赛事"));
        competitions.add(createCompetition(59, "时报金犊奖", "重点赛事"));
        competitions.add(createCompetition(60, "中国高等院校设计作品大赛", "重点赛事"));
        competitions.add(createCompetition(61, "中国好创意暨全国数字艺术设计大赛", "重点赛事"));
        competitions.add(createCompetition(62, "未来设计师·全国高校数字艺术设计大赛", "重点赛事"));
        competitions.add(createCompetition(63, "中国大学生计算机设计大赛", "重点赛事"));
        competitions.add(createCompetition(64, "全国三维数字化设计创新大赛", "重点赛事"));
        competitions.add(createCompetition(65, "IDEA工业设计优秀奖", "重点赛事"));
        competitions.add(createCompetition(66, "IF设计奖", "重点赛事"));
        competitions.add(createCompetition(67, "红点设计奖", "重点赛事"));
        competitions.add(createCompetition(68, "中国大学生工程实践与创新能力大赛", "重点赛事"));
        competitions.add(createCompetition(69, "全国大学生能源经济学术创意大赛", "重点赛事"));
        competitions.add(createCompetition(70, "全国高校商业精英挑战赛国际贸易竞赛", "重点赛事"));
        competitions.add(createCompetition(71, "全国高校商业精英挑战赛系列竞赛", "重点赛事"));
        competitions.add(createCompetition(72, "全国管理案例精英赛", "重点赛事"));
        competitions.add(createCompetition(73, "中国MPAcc学生案例大赛", "重点赛事"));
        competitions.add(createCompetition(74, "\"东方财富杯\"全国大学生金融精英挑战赛", "重点赛事"));
        competitions.add(createCompetition(75, "国际商务专业学位研究生案例竞赛", "重点赛事"));
        competitions.add(createCompetition(76, "全国高校企业价值创造实战竞赛", "重点赛事"));
        competitions.add(createCompetition(77, "\"21世纪杯\"全国英语演讲比赛", "重点赛事"));
        competitions.add(createCompetition(78, "\"外教社杯\"全国高校学生跨文化能力大赛", "重点赛事"));
        competitions.add(createCompetition(79, "\"外研社·国才杯\"\"理解当代中国\"全国大学生外语能力大赛", "重点赛事"));
        competitions.add(createCompetition(80, "全国大学生英语竞赛", "重点赛事"));
        competitions.add(createCompetition(81, "中华经典诵写讲大赛", "重点赛事"));
        competitions.add(createCompetition(82, "全国大学生市场调查与分析大赛", "重点赛事"));
        competitions.add(createCompetition(83, "全国大学生统计建模大赛", "重点赛事"));
        competitions.add(createCompetition(84, "全国应用统计专业学位研究生案例大赛", "重点赛事"));
        competitions.add(createCompetition(85, "全国大学生嵌入式芯片与系统设计竞赛", "重点赛事"));
        competitions.add(createCompetition(86, "全国大学生光电设计竞赛", "重点赛事"));
        competitions.add(createCompetition(87, "\"田家炳杯\"全日制教育硕士专业学位研究生教学技能大赛", "重点赛事"));

        // 一般赛事（88-133）
        competitions.add(createCompetition(88, "中国农业机器人大赛", "一般赛事"));
        competitions.add(createCompetition(89, "中国包装创意设计大赛", "一般赛事"));
        competitions.add(createCompetition(90, "湖北省大学生化学化工学术创新成果报告会", "一般赛事"));
        competitions.add(createCompetition(91, "全国大学生化工过程数字创新竞赛", "一般赛事"));
        competitions.add(createCompetition(92, "湖北省高校大学生酒体设计大赛", "一般赛事"));
        competitions.add(createCompetition(93, "全国大学生白酒品酒技能大赛", "一般赛事"));
        competitions.add(createCompetition(94, "中国食品科学技术学会大学生创新大赛系列赛", "一般赛事"));
        competitions.add(createCompetition(95, "大学生建造智能与管理创新竞赛", "一般赛事"));
        competitions.add(createCompetition(96, "\"U+L新思维\"概念设计竞赛", "一般赛事"));
        competitions.add(createCompetition(97, "全国大学生工业化建筑与智慧建造竞赛", "一般赛事"));
        competitions.add(createCompetition(98, "国际学生高层建筑设计大赛", "一般赛事"));
        competitions.add(createCompetition(99, "MathorCup高校数学建模挑战赛", "一般赛事"));
        competitions.add(createCompetition(100, "国际大学生I CAN创新创业大赛", "一般赛事"));
        competitions.add(createCompetition(101, "湖北省大学生信息技术创新大赛", "一般赛事"));
        competitions.add(createCompetition(102, "东方设计奖·全国高校创新设计大赛", "一般赛事"));
        competitions.add(createCompetition(103, "湖北省大学生文化创意设计大赛", "一般赛事"));
        competitions.add(createCompetition(104, "中国大学生广告艺术节学院奖", "一般赛事"));
        competitions.add(createCompetition(105, "艾景奖", "一般赛事"));
        competitions.add(createCompetition(106, "\"黄鹤杯\"工业设计大赛", "一般赛事"));
        competitions.add(createCompetition(107, "楚天杯工业设计大赛", "一般赛事"));
        competitions.add(createCompetition(108, "德国博朗奖The BraunPrize", "一般赛事"));
        competitions.add(createCompetition(109, "全国大学生机械创新设计大赛", "一般赛事"));
        competitions.add(createCompetition(110, "全国大学生数字媒体科技作品及创意竞赛", "一般赛事"));
        competitions.add(createCompetition(111, "中国高校智能机器人创意大赛", "一般赛事"));
        competitions.add(createCompetition(112, "中国玩具和婴童用品创意设计大赛", "一般赛事"));
        competitions.add(createCompetition(113, "中美青年创客大赛", "一般赛事"));
        competitions.add(createCompetition(114, "CMUU市场研究与商业策划大赛", "一般赛事"));
        competitions.add(createCompetition(115, "湖北省MPAcc案例大赛", "一般赛事"));
        competitions.add(createCompetition(116, "全国本科院校税收风险管控案例大赛", "一般赛事"));
        competitions.add(createCompetition(117, "全国跨境电商创新创业能力大赛（秋季赛）", "一般赛事"));
        competitions.add(createCompetition(118, "湖北省研究生公共管理案例大赛", "一般赛事"));
        competitions.add(createCompetition(119, "湖北高校马克思主义理论学科研究生论坛", "一般赛事"));
        competitions.add(createCompetition(120, "湖北省\"导航杯\"中南联盟实践教育活动校际决赛", "一般赛事"));
        competitions.add(createCompetition(121, "\"汉教英雄会\"国际中文教学技能全国交流活动", "一般赛事"));
        competitions.add(createCompetition(122, "\"求是杯\"国际诗歌创作与翻译大赛", "一般赛事"));
        competitions.add(createCompetition(123, "\"外教社·词达人杯\"全国大学生英语词汇能力大赛", "一般赛事"));
        competitions.add(createCompetition(124, "CATTI杯全国翻译大赛", "一般赛事"));
        competitions.add(createCompetition(125, "韩素音国际翻译大赛", "一般赛事"));
        competitions.add(createCompetition(126, "湖北省翻译大赛", "一般赛事"));
        competitions.add(createCompetition(127, "全国大学生语言文字能力大赛", "一般赛事"));
        competitions.add(createCompetition(128, "全国口译大赛", "一般赛事"));
        competitions.add(createCompetition(129, "中国大学生5分钟科研英语演讲竞赛", "一般赛事"));
        competitions.add(createCompetition(130, "中西部外语翻译大赛", "一般赛事"));
        competitions.add(createCompetition(131, "\"泰迪杯\"全国数据挖掘挑战赛", "一般赛事"));
        competitions.add(createCompetition(132, "企校协同创新大赛", "一般赛事"));
        competitions.add(createCompetition(133, "\"华文杯\"全国师范院校师范生数学技能大赛", "一般赛事"));

        return competitions;
    }

    private CompetitionDictModel createCompetition(Integer serialNumber, String competitionName, String competitionLevel) {
        CompetitionDictModel competition = new CompetitionDictModel();
        competition.setSerialNumber(serialNumber);
        competition.setCompetitionName(competitionName);
        competition.setCompetitionLevel(competitionLevel);
        competition.setCompetitionStatus("启用");  
        return competition;
    }

    @Override
    public List<CompetitionDictModel> searchCompetitions(String keyword) {
        return competitionDictRepository.findByCompetitionNameContaining(keyword);
    }

    @Override
    public List<CompetitionDictModel> getAllActiveCompetitions() {
        return competitionDictRepository.findByCompetitionStatus("启用"); 
    }

    @Override
    public CompetitionDictModel getCompetitionByName(String name) {
        return competitionDictRepository.findByCompetitionName(name);
    }

    @Override
    public CompetitionDictModel addCompetition(CompetitionDictModel competition) {
        if (competition.getCompetitionStatus() == null) {
            competition.setCompetitionStatus("启用");
        }
        return competitionDictRepository.save(competition);
    }

    @Override
    public CompetitionDictModel updateCompetition(CompetitionDictModel competition) {
        return competitionDictRepository.save(competition);
    }

    @Override
    public void deleteCompetition(String id) {
        competitionDictRepository.deleteById(id);
    }

    @Override
    public List<CompetitionDictModel> getAllCompetitions() {
        return competitionDictRepository.findAll();
    }
}