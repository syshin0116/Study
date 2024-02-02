<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>

   		<!-- 헤더 -->
    	<jsp:include page="../common/header.jsp" />
    	
        <section class="content">

            <div class="tit-con clear">
                <h2 class="f-l col-navy">Community</h2>
                <div class="f-r ma-t-5">
                    <a href="/">Home</a> > Community
                </div>
            </div>

            <div class="ma-t-15 clear">
                <button class="btn btn-1 f-r" onclick="location.href='/Community/BoardReg'">Write</button>
                <button class="btn btn-2 f-l" onclick="location.href='/Community/BoardList'">List</button>
            </div>
            
            <div class="con-list ma-t-5">
                <ul>
                    <li>
                        <div class="clear">
                            <div class="list-no"><strong>NOTICE</strong></div>
                            <div class="list-tit">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer et blandit nisi. Phasellus convallis, arcu eu efficitur viverra, leo nisl vestibulum tortor, quis vehicula turpis magna at nunc. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; In vitae ex venenatis, luctus eros at, tincidunt quam.</div>
                            <div class="list-writer">Aliquam</div>
                            <div class="list-etc">
                                <span>Comment : <strong>21</strong></span>
                                <span class="sep">|</span>
                                <span>Like : <strong>104</strong></span>
                                <span class="sep">|</span>
                                <span>Hit : <strong>12067</strong></span>
                                <span class="sep">|</span>
                                <span>Date : <strong>2023-09-20 16:43</strong></span>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="con-detail">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas sit amet nibh arcu. Aenean blandit, enim sed aliquam consectetur, lorem arcu suscipit metus, nec maximus nunc diam vitae arcu. Maecenas nec lectus sapien. Praesent fringilla dignissim orci viverra feugiat. Vivamus efficitur, velit in porttitor maximus, odio enim eleifend urna, ac sodales libero nisi eu odio. Maecenas et ipsum semper, feugiat odio quis, mollis est. Vivamus commodo ipsum non semper tempus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus a varius mi, vel posuere est. Etiam magna tortor, venenatis eget est consequat, lacinia tincidunt lorem. Suspendisse potenti.

                            Suspendisse potenti. Curabitur non tincidunt quam, ut tristique erat. Fusce molestie convallis risus sed auctor. Ut aliquet sollicitudin neque et sodales. Donec pellentesque felis vel neque pellentesque molestie. Maecenas vitae nulla quis dui finibus condimentum. Morbi viverra ultricies ante ut auctor. Maecenas imperdiet tellus ac vulputate dignissim. In eget urna ut lorem tempor placerat vel in eros. Morbi vestibulum varius lacus, facilisis pulvinar odio ullamcorper id. Etiam dapibus sollicitudin ante porttitor commodo. Cras lobortis, arcu sit amet dapibus faucibus, mauris urna ornare erat, eget iaculis velit nisi a nunc. Sed in cursus orci, nec lobortis ligula.
                            
                            Maecenas placerat accumsan risus sed interdum. Praesent dapibus at erat at condimentum. Nulla fermentum at massa in tincidunt. Donec ut porta dolor, id interdum sem. Phasellus mattis sem sed diam maximus, id commodo mi interdum. Fusce vehicula, magna id dignissim sollicitudin, nulla nibh lobortis elit, a consequat arcu purus sed odio. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Morbi condimentum, orci ac convallis iaculis, mi enim dignissim magna, eu aliquet dolor neque sed felis.
                            
                            Cras vel dolor cursus, molestie nunc vitae, venenatis odio. Nulla sed elit non diam malesuada laoreet et eget nisl. Nam fermentum, magna pharetra auctor luctus, sem sem venenatis risus, vulputate laoreet velit est eu nulla. Etiam nec tincidunt nisi, sed tempus nisl. Fusce bibendum tempus mauris, vel efficitur risus dictum et. Nunc laoreet ultricies erat, sit amet porttitor dolor tristique at. Ut fermentum bibendum libero, at rutrum turpis feugiat et. Nullam tincidunt mollis metus, eu maximus nulla iaculis ut. Aenean erat arcu, pharetra a nibh at, molestie tincidunt elit. Sed metus mauris, accumsan nec euismod quis, pellentesque sit amet libero. Integer nec urna ligula. Donec lobortis libero nec laoreet elementum.
                            
                            Donec aliquam justo vel semper laoreet. Nunc euismod nisi vitae volutpat porttitor. Sed sodales nisl eget diam tincidunt varius. Cras sapien turpis, accumsan ut feugiat sit amet, laoreet in tellus. Phasellus imperdiet nisi dolor, non posuere magna viverra ac. Pellentesque quis pharetra magna. Ut id volutpat sapien, quis porttitor massa.
                            
                            Praesent congue pellentesque est, in vestibulum sapien viverra id. Mauris quam metus, porta eget faucibus in, consequat id urna. Suspendisse et elit mi. Phasellus vulputate maximus ornare. Morbi nibh dui, suscipit ac leo non, euismod ultricies eros. Suspendisse ultricies lacinia nisi in tempus. Integer vel tristique tortor. Curabitur efficitur lacus et elit efficitur, eu cursus nisi venenatis. Pellentesque augue est, dignissim vitae odio non, aliquet porttitor erat. Integer non orci eros. Cras ut nunc lorem. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Proin consequat dui sed risus condimentum, a aliquet ipsum pellentesque. Cras porta tincidunt justo, ut tempor orci sagittis vitae. Proin mollis sapien non rutrum consectetur.
                            
                            Donec et tortor eget tellus vestibulum cursus. Maecenas congue tellus turpis, eu semper nulla vehicula at. Etiam a eleifend magna. Sed pharetra laoreet elit, sit amet sodales ante. Nam condimentum facilisis odio, vitae semper eros viverra vitae. Praesent id vehicula arcu, sit amet mattis nisi. Mauris ut fringilla diam, ac mattis neque. Pellentesque ut sem mi. Fusce sagittis, est eu tempor porttitor, metus risus convallis mauris, vel suscipit orci tortor ac sem. Integer elementum neque arcu, ut lobortis lectus ullamcorper eget. Integer tristique suscipit orci vel luctus. Praesent nec nunc elit. Aenean et ante id dui scelerisque venenatis quis eget dolor. Donec mattis dui tincidunt ultricies consectetur. Duis urna tellus, lacinia in convallis quis, efficitur et metus.
                            
                            Integer vel euismod velit. Sed feugiat tellus pharetra volutpat suscipit. Nam faucibus, orci ut hendrerit facilisis, est massa placerat elit, in fringilla velit lectus eget libero. Fusce tincidunt, turpis dapibus maximus lacinia, libero nibh gravida arcu, ut feugiat ligula dui at nibh. Ut mauris tortor, sagittis quis imperdiet eget, pretium non nisi. Maecenas viverra tortor lorem, id maximus sapien tempor a. Suspendisse non pharetra magna. Proin eget ornare nisl. Pellentesque vel quam diam. Praesent fermentum odio erat, nec sagittis nisl euismod a.
                            
                            Duis congue varius libero vel iaculis. Duis pretium nulla quis eros sollicitudin, id sollicitudin magna maximus. Proin aliquet, dolor eu maximus pharetra, metus libero laoreet ex, et lobortis nisl ante sit amet lorem. Nullam pretium aliquam est, eu facilisis justo imperdiet non. Aenean iaculis metus turpis, eget commodo mauris maximus ut. Sed vehicula sollicitudin purus, ac suscipit turpis fermentum eu. Sed pellentesque auctor est, nec imperdiet augue blandit eu. In quis gravida erat.
                            
                            Aenean ut leo eget eros congue dictum et ac ante. Integer sit amet tempor massa. Quisque ultrices imperdiet orci, sed pulvinar metus facilisis eu. Duis sagittis ipsum efficitur massa condimentum posuere. Nullam id tincidunt magna. Etiam ut imperdiet tortor. Proin eget luctus enim, et facilisis mauris. Pellentesque eu nibh id mauris pellentesque maximus non vitae dolor. Duis cursus finibus ullamcorper. Curabitur vel pretium eros. Aliquam eu congue ex. Morbi commodo finibus dui, id iaculis purus luctus eget. Etiam rhoncus rhoncus tempor.
                            
                            Vivamus ac laoreet tellus, pretium egestas velit. Maecenas pharetra, neque in eleifend vestibulum, erat dui blandit leo, vel feugiat ipsum purus at lectus. Duis arcu orci, maximus in diam at, sollicitudin interdum mi. Fusce consectetur aliquet enim. Mauris consequat mollis posuere. Nullam vulputate sed libero at posuere. Cras sed tortor vestibulum, porta ipsum vitae, blandit lacus. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.
                            
                            Quisque pulvinar tristique ante at porttitor. Vestibulum luctus tortor ut tristique maximus. Phasellus vestibulum feugiat est, non fermentum magna feugiat ut. Sed sed ligula id lorem accumsan sollicitudin. Pellentesque tempus lectus non hendrerit pulvinar. Quisque euismod urna erat, a consequat ante ultrices vitae. Quisque vitae nunc et risus tempus sodales. Donec eu nisi a nulla finibus mattis eu dapibus dui. Pellentesque commodo sapien in odio varius feugiat. Ut dictum libero non interdum euismod. Morbi at lacinia ex. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Curabitur non nulla dignissim felis venenatis faucibus sed vel quam. Nulla porta porta dictum.
                            
                            Fusce lobortis augue eu lacus auctor, a viverra sapien mollis. In hac habitasse platea dictumst. Quisque ut porttitor tortor. Nam vel quam vitae eros eleifend congue eu vel magna. Nunc auctor fringilla nibh id elementum. Vestibulum faucibus ipsum bibendum nibh euismod rhoncus. Cras varius placerat metus, vitae ultricies urna convallis tempor.
                            
                            Integer varius suscipit sem nec malesuada. Maecenas vel tellus vestibulum, tincidunt ipsum ac, hendrerit enim. Vivamus iaculis blandit ante id consequat. Aenean at malesuada lorem. Praesent dignissim purus id nisl semper finibus. Praesent laoreet neque quam. Integer a pulvinar velit. Vivamus rhoncus, massa ac aliquet pulvinar, ex purus molestie felis, at ornare risus massa a metus. Proin dictum consequat lorem. Mauris dictum at lacus vel ornare. Nulla auctor tempus nibh, ut dapibus dui. Mauris in vehicula nulla, vitae tempor ex. Duis ut tempus lacus, nec hendrerit orci. Integer risus lorem, congue at leo vel, condimentum venenatis felis. Vestibulum risus tortor, hendrerit ut ex id, fermentum tincidunt odio.
                            
                            Vestibulum at nulla id enim mollis pellentesque. Mauris et neque eleifend, ornare justo ac, cursus orci. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; In nec lectus a enim aliquet ornare. Praesent libero massa, gravida eget ullamcorper vitae, aliquet vitae lacus. Nullam ut gravida nulla. Cras posuere ligula at augue suscipit ultricies. Nulla facilisi. Aenean sed pulvinar lorem. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Ut vitae tellus ac justo aliquam eleifend id sit amet diam. Vestibulum cursus in felis a aliquet. Nulla tincidunt risus sit amet ligula posuere finibus. Cras vitae accumsan leo.
                        </div>
                        <div class="a-c ma-t-10 ma-b-15">
                            <button class="btn btn-like">Like</button>
                        </div>
                    </li>
                </ul>
            </div>

            <div class="ma-t-10 clear">
                <button class="btn btn-1 f-r" onclick="location.href='/Community/BoardReg'">Write</button>
                <button class="btn btn-2 f-r ma-r-5" onclick="location.href='/Community/BoardList'">Delete</button>
                <button class="btn btn-2 f-l" onclick="location.href='/Community/BoardList'">List</button>
            </div>

            <div class="comm-list ma-t-20">
                <div class="count">Comment : <strong>21</strong></div>
                <ul class="ma-t-5">
                    <li>
                        <div class="comm-writer">Quisque pulvinar</div>
                        <div class="comm-date">2023-09-20 17:31</div>
                        <div class="comm-del">
                            <button class="btn btn-comm-del">Delete</button>
                        </div>
                        <div class="comment">Integer varius suscipit sem nec malesuada. Maecenas vel tellus vestibulum, tincidunt ipsum ac, hendrerit enim. Vivamus iaculis blandit ante id consequat. Aenean at malesuada lorem. Praesent dignissim purus id nisl semper finibus. Praesent laoreet neque quam. Integer a pulvinar velit. Vivamus rhoncus, massa ac aliquet pulvinar, ex purus molestie felis, at ornare risus massa a metus. Proin dictum consequat lorem. Mauris dictum at lacus vel ornare. Nulla auctor tempus nibh, ut dapibus dui. Mauris in vehicula nulla, vitae tempor ex. Duis ut tempus lacus, nec hendrerit orci. Integer risus lorem, congue at leo vel, condimentum venenatis felis. Vestibulum risus tortor, hendrerit ut ex id, fermentum tincidunt odio.</div>
                    </li>
                    <li>
                        <div class="comm-writer">hendrerit</div>
                        <div class="comm-date">2023-09-20 17:31</div>
                        <div class="comment">Integer varius suscipit sem nec malesuada. Maecenas vel tellus vestibulum, tincidunt ipsum ac, hendrerit enim. Vivamus iaculis blandit ante id consequat. Aenean at malesuada lorem. Praesent dignissim purus id nisl semper finibus. Praesent laoreet neque quam. Integer a pulvinar velit. Vivamus rhoncus, massa ac aliquet pulvinar, ex purus molestie felis, at ornare risus massa a metus. Proin dictum consequat lorem. Mauris dictum at lacus vel ornare. Nulla auctor tempus nibh, ut dapibus dui. Mauris in vehicula nulla, vitae tempor ex. Duis ut tempus lacus, nec hendrerit orci. Integer risus lorem, congue at leo vel, condimentum venenatis felis. Vestibulum risus tortor, hendrerit ut ex id, fermentum tincidunt odio.</div>
                    </li>
                    <li>
                        <div class="comm-writer">Integer</div>
                        <div class="comm-date">2023-09-20 17:31</div>
                        <div class="comment">Duis ut tempus lacus, nec hendrerit orci. Integer risus lorem, congue at leo vel, condimentum venenatis felis. Vestibulum risus tortor, hendrerit ut ex id, fermentum tincidunt odio.</div>
                    </li>
                    </li>
                    <li>
                        <div class="comm-writer">congue</div>
                        <div class="comm-date">2023-09-20 17:31</div>
                        <div class="comment">Duis ut tempus lacus, nec hendrerit orci. Integer risus lorem, congue at leo vel, condimentum venenatis felis. Vestibulum risus tortor, hendrerit ut ex id, fermentum tincidunt odio.</div>
                    </li>
                    </li>
                    <li>
                        <div class="comm-writer">pulvinar</div>
                        <div class="comm-date">2023-09-20 17:31</div>
                        <div class="comment">Duis ut tempus lacus, nec hendrerit orci. Integer risus lorem, congue at leo vel, condimentum venenatis felis. Vestibulum risus tortor, hendrerit ut ex id, fermentum tincidunt odio.</div>
                    </li>
                    </li>
                    <li>
                        <div class="comm-writer">tempus</div>
                        <div class="comm-date">2023-09-20 17:31</div>
                        <div class="comment">Duis ut tempus lacus, nec hendrerit orci. Integer risus lorem, congue at leo vel, condimentum venenatis felis. Vestibulum risus tortor, hendrerit ut ex id, fermentum tincidunt odio.</div>
                    </li>
                </ul>
                <div class="comm-reg">
                    <textarea placeholder="Comment here."></textarea>
                    <button class="btn">Register</button>
                </div>
            </div>
        </section>
        
    	<jsp:include page="../common/footer.jsp" />