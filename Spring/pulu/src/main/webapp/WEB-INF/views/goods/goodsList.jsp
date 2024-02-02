<%@ page language="java" pageEncoding="UTF-8"%>
<!-- 상품리스트는 상품리스트 작업자들끼리 의논하고나서 구현하는게 편할것 같아서  -->
<!-- 일단 눈에 보이게만 작업해달라고 부탁해서 의종님이 작업해주셨습니다. 감사합니다. -->
<!-- 전부 임시이고 작업할때 제가 다시 만들도록 하겠습니다. -->
<div id="content">
	<div id="header">
		<div id="SaladAmount">
			상품수량
		</div>
		<div id="SaladCategory">
			<div id="select" align="right">
				<select name="job" align="right">
					<option value="상품명">상품명</option>
					<option value="낮은가격">낮은가격</option>
					<option value="높은가격">높은가격</option>
					<option value="추천상품">추천상품</option>
				</select>
			</div>
		</div>
	</div>


	<div id="saladList">
			<table style="float: left;">
				<tr>
					<td><img
						src="C:\Java\Ezen (2)\src\main\webapp\resources\img\testlist.jpg"
						width="300" height="300"></td>
				</tr>
				<tr align=header>
				</tr>
				<tr>
					<td>상품명</td>
				</tr>
				<tr>
					<td>가격</td>
				</tr>
			</table>
			<table style="float: left;">
				<tr>
					<td><img
						src="C:\Java\Ezen (2)\src\main\webapp\resources\img\testlist.jpg"
						width="300" height="300"></td>
				</tr>
				<tr align=header>
				</tr>
				<tr>
					<td>상품명</td>
				</tr>
				<tr>
					<td>가격</td>
				</tr>
			</table>
			<table style="float: left;">
				<tr>
					<td><img
						src="C:\Java\Ezen (2)\src\main\webapp\resources\img\testlist.jpg"
						width="300" height="300"></td>
				</tr>
				<tr align=header>
				</tr>
				<tr>
					<td>상품명</td>
				</tr>
				<tr>
					<td>가격</td>
				</tr>
			</table>
	</div>
	<div class="dummy"><!-- 지워야하는 더미 데이터 -->
		<table height="900">
		<tr><td>&nbsp;</td></tr>
		</table>
	</div>
</div>
